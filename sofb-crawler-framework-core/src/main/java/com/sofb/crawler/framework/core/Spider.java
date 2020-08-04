package com.sofb.crawler.framework.core;

import com.sofb.crawler.framework.core.base.AbstractSpider;
import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.*;
import com.sofb.crawler.framework.core.util.StringUtil;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.DOMException;

import java.util.*;

import static com.sofb.crawler.framework.core.model.Request.isValidRequest;

/**
 * @author liuxuejun
 * @date 2019-10-16 17:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Spider extends AbstractSpider {


    //从platformPay从装载必要的信息
    public static Spider platformPayLoadCovert(PlatformPayLoad platformPayLoad) {

        //初始化一个监听器
        platformPayLoad.initDefaultSetting();

        Spider spider = new Spider();
        //开启异步
        spider.setAsyncRun(platformPayLoad.isAsyncRun());
        //下载器
        spider.setDownloader(platformPayLoad.getDownloader());
        //调度器
        spider.setScheduler(platformPayLoad.getSchedule());
        //线程数
        spider.setThreadNum(platformPayLoad.getThreadNum());

        //订制爬虫运行时的参数
        SpiderContext spiderContext = new SpiderContext();
        spiderContext.setProjectName(platformPayLoad.getProjectName());
        spiderContext.setPlatformName(platformPayLoad.getPlatformName());

        //优先级队列保证
        List<String> queueList = new ArrayList<>();
        for (String suffix : platformPayLoad.getTaskQueueSuffix()) {

            //优先级队列保证
            for (String consumeType : platformPayLoad.getConsumeTypes()) {
                queueList.add(
                        StringUtils.join(
                                Arrays.asList(
                                        platformPayLoad.getTaskQueuePrefix(),
                                        platformPayLoad.getProjectName(),
                                        platformPayLoad.getPlatformName(),
                                        consumeType,
                                        suffix),
                                ":"));
            }
        }

        //起始URL
        spider.addRequest(platformPayLoad.getStartRequest());
        //优先级排序后
        spiderContext.setQueueList(queueList);
        spiderContext.setCity(platformPayLoad.getCity());
        spiderContext.setIncludeTables(platformPayLoad.getIncludeTables());
        spiderContext.setExcludeTables(platformPayLoad.getExcludeTables());
        spiderContext.setExcludeTypes(platformPayLoad.getExcludeTypes());
        spiderContext.setIncludeTypes(platformPayLoad.getIncludeTypes());
        spiderContext.setPrefix(platformPayLoad.getTaskQueuePrefix());
        spiderContext.setExtension(platformPayLoad.getExtension());
        spiderContext.setUseProxyRate(platformPayLoad.getUseProxyRate());

        spider.setSpiderContext(spiderContext);

        //回调函数
        spider.setCallBackManager(platformPayLoad.getCallBackManager());
        spider.setPipeLineManager(platformPayLoad.getPipeLineManager());
        spider.setGlobalListenerManager(platformPayLoad.getGlobalListenerManager());
        spider.setDownloadListenerManager(platformPayLoad.getDownloadListenerManager());
        spider.setAbleKeepAlive(platformPayLoad.isAbleKeepAlive());
        return spider;
    }

    public static void runWithPlatformPayLoad(PlatformPayLoad platformPayLoad) {

        platformPayLoadCovert(platformPayLoad).start();
    }

    @Override
    public void addRequest(List<Request> requests) {
        requests.forEach(this::addRequest);
    }

    public void addRequest(Request request) {
        /* 将request回推到队列 */
        this.getScheduler().push(request);
    }


    @Override
    public void crawlPage() {
        spiderContext.setAbleKeepAlive(this.isAbleKeepAlive());
        SpiderContextThreadLocalStore.set(spiderContext);
        while (true) {
            /* 从队列中获取request */
            Request request = null;
            Response response = null;
            try {
                request = this.getScheduler().poll();
                if (!isValidRequest(request)) {
                    if (this.isAbleKeepAlive()) {
                        long sleepTime = new Random().nextInt(100) * 500;
                        log.info("{} {} 队列没有任务 休眠 {} 毫秒等待新任务!!!", spiderContext.getProjectName(), spiderContext.getPlatformName(), sleepTime);

                        Thread.sleep(sleepTime);

                        continue;
                    } else {
                        log.info("队列没有任务,爬虫任务结束");
                        break;
                    }
                }
                this.getGlobalListenerManager().afterInit(request);
                // 下载
                response = handleDonwloadRequest(request);
                handleParseResponse(response);
                handleProcessResponse(request,response);

                this.getGlobalListenerManager().onSuccess(request, response);
            } catch (CrawlerException e) {
                this.getGlobalListenerManager().onError(request, response, e);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private  void  handleParseResponse(Response response){
        try {
            /* 处理得到resultItem */
            this.getCallBackManager().getFunction(response.getCallBackName()).call(response);
        }catch (RuntimeException e){
            throw new CrawlerException(CrawlerErrorCodeEnum.PROCESS_RESPONSE_ERROR,e);
        }

    }

    private void   handleProcessResponse(Request request,Response response){

        ResultItems resultItem=response.getResultItems();
        try {
            /* 处理result进行处理 */
            if (resultItem != null && resultItem.getItems() != null) {
                resultItem.setMsg(response.getMsg());
                resultItem.setInvalidPage(response.isInvalidPage());
                resultItem.setExtras(response.getExtras());
                resultItem.setConsumeType(response.getExtra(RequestConstant.CONSUME_TYPE).toString());
                resultItem.setBatchId(Long.parseLong(Optional.ofNullable(response.getExtra(RequestConstant.BATCH_ID)).orElse("0").toString()));
                this.getPipeLineManager().getAllListener().forEach(m -> m.process(resultItem));
            }
            List<Request> newRequests = response.getRequestList();
            if (CollectionUtils.isNotEmpty(newRequests)) {
                log.info("请求 {} 生成新的请求 {}个 ", request.getUrl(), newRequests.size());
                addRequest(newRequests);
            }
        }catch (RuntimeException e) {
            throw new CrawlerException(CrawlerErrorCodeEnum.PROCESS_RESPONSE_ERROR, e);
        }
    }


    /**
     * @param request 请求
     * @return response
     */
    private Response handleDonwloadRequest(Request request) {
        try {
            /* 下载获取response */
            long st = System.currentTimeMillis();
            getDownloadListenerManager().preDownload(request);
            Response response = this.getDownloader().download(request);
            getDownloadListenerManager().afterDownload(response);
            log.info("链接 {} 下载花费 {} 毫秒", request.getUrl(), System.currentTimeMillis() - st);
            return response;
        }catch (RuntimeException e){
            throw new CrawlerException(CrawlerErrorCodeEnum.DOWNLOAD_ERROR,e);
        }

    }
}
