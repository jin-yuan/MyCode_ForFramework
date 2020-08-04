package com.sofb.crawler.framework.core.listener.global;

import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import static com.sofb.crawler.framework.core.model.Request.isValidRequest;

/**
 * @author liuxuejun
 * @date 2019-12-20 10:30
 */
@Slf4j
public class DefaultGlobalListener implements GlobalListener {

    private Scheduler scheduler;


    private int maxRetryTimes;


    public DefaultGlobalListener(Scheduler scheduler, int maxRetryTimes) {
        this.scheduler = scheduler;
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public void beforeInit() {
    }

    @Override
    public void afterInit(Request request) {
        if (!isValidRequest(request)) {
            return;
        }
        request.putStartTime(System.currentTimeMillis());
    }

    @Override
    public void onSuccess(Request request, Response response) {

        log.info("成功处理请求 {} 解析花费 {} 毫秒", request.getUrl(), System.currentTimeMillis() - request.fetchStartTime());
    }

    @Override
    public void onError(Request request, Response response, CrawlerException e) {

        if (!isValidRequest(request)) {
            log.error("非法的request请求，不给予重推");
            return;
        }
        if (response == null) {
            response = new Response();
        }
        int errorTimes = Integer.parseInt(request.getExtra(RequestConstant.ERROR_TIMES).toString());
        if (errorTimes < maxRetryTimes && isValidRequest(request)) {
            if ("400".equals(e.getErrorCode().getCode()) || "300".equals(e.getErrorCode().getCode())) {
                log.error("{} 下载异常 {}", response.getUrl(), e);
            }else {
                log.error("{} 非下载异常 {}", response.getUrl(), e.toString());
                errorTimes++;
            }
            request.putExtra(RequestConstant.ERROR_TIMES, errorTimes);
            scheduler.push(request);
            log.error("请求 {} 发生异常 {} ，重试 {} 次  响应内容为 {}", request.getUrl(), e, errorTimes, Optional.ofNullable(response).orElse(new Response()).getText());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new CrawlerException(CrawlerErrorCodeEnum.UNSPECIFIED, "在处理错误任务时发生的未知的错误", e);
        }

    }

    @Override
    public void unCatch(Request request, Response response, CrawlerException e) {
        try {
            log.error(
                    "机器{} -进程 {} 请求 {}  响应 {}发生异常 {}，休息 100秒",
                    InetAddress.getLocalHost().getHostAddress(),
                    Thread.currentThread().getName(), request, response.getText().substring(0, 1000), e);

        } catch (RuntimeException | UnknownHostException e1) {
            e1.printStackTrace();
        }

        try {
            Thread.sleep(100000);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }


}

