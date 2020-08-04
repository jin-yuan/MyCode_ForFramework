package com.sofb.crawler.framework.core.base;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.download.HttpClientDownload;
import com.sofb.crawler.framework.core.listener.callback.CallBackManager;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListenerManager;
import com.sofb.crawler.framework.core.listener.global.GlobalListenerManager;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.SpiderContext;
import com.sofb.crawler.framework.core.pipeline.ConsolePipeline;
import com.sofb.crawler.framework.core.pipeline.PipeLineManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 爬虫状态的管理
 *
 * @author liuxuejun
 * @date 2019-11-08 15:41
 */
@Data
@Slf4j
public abstract class AbstractSpider {

    //爬虫上下文中涵盖的任务信息
    public SpiderContext spiderContext;

    //下载器
    private Downloader downloader = new HttpClientDownload();

    //调度器
    private Scheduler scheduler;

    private CallBackManager callBackManager = new CallBackManager();

    //持久化管理
    private PipeLineManager pipeLineManager = new PipeLineManager();

    //下载监听器
    private DownloadListenerManager downloadListenerManager = new DownloadListenerManager();

    //全局监听器
    private GlobalListenerManager globalListenerManager = new GlobalListenerManager();

    //线程池
    private ThreadPoolExecutor threadPool;
    private int threadNum = 1;

    //异步
    private boolean asyncRun = true;

    //保持运行存活
    private boolean ableKeepAlive = false;

    /**
     * 爬虫入口
     */
    public void start() {

        //this::function代表这个函数对象的方法，也可以用Object::function的方式。其中this::fuction不可以用在static方法上，而Object::function是用在Object类的static方法中。
        //从线程池里面拉取任务
        Thread thread = new Thread(this::threadPoolCrawler);

        thread.start();
        if (!asyncRun) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 线程池处理爬虫任务
     */
    private void threadPoolCrawler() {

        initComponent();

        for (int i = 0; i < threadNum; i++) {
            threadPool.execute(this::crawlPage);
        }

        threadPool.shutdown();

        while (true) {
            try {
                if (threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS)) {

                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 实际的爬虫抓取流程
     */
    public abstract void crawlPage();

    /**
     * 增加任务
     *
     * @param requests request list
     */
    public abstract void addRequest(List<Request> requests);

    private void initComponent() {

        //爬虫对应信息
        log.info("项目{} 工程 {}线程池初始化 线程池 {} ", spiderContext.getProjectName(), spiderContext.getPlatformName(), threadPool);

        //线程池为空或者禁止状态
        if (threadPool == null || threadPool.isShutdown()) {

            threadPool = new ThreadPoolExecutor(threadNum, threadNum,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat(spiderContext.getProjectName() + spiderContext.getPlatformName() + "%d").build(), new ThreadPoolExecutor.DiscardPolicy());

            log.info("项目{} 工程 {}创建固定个数 {}线程池 ", spiderContext.getProjectName(), spiderContext.getPlatformName(), threadNum);
        }

        if (CollectionUtils.isEmpty(pipeLineManager.getAllListener())) {
            pipeLineManager.addListener(new ConsolePipeline());
        }
    }
}
