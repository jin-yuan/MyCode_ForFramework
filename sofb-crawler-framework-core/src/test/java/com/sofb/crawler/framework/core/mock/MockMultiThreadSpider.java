package com.sofb.crawler.framework.core.mock;

import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.base.SpiderContextThreadLocalStore;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.SpiderContext;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 模仿多线程爬虫
 *
 * @author liuxuejun
 * @date 2019-11-11 14:52
 */
@Data
public class MockMultiThreadSpider {

    private Scheduler scheduler = new com.sofb.crawler.framework.core.mock.MockSchedule();

    private Downloader downloader = new com.sofb.crawler.framework.core.mock.MockDownload();

    private SpiderContext spiderContext;

    private boolean asyncRun;

    private int threadNum = 2;

    public void start() {

        CompletableFuture future = CompletableFuture.runAsync(this::threadPoolCrawler);

        if (!asyncRun) {
            future.join();
        }
    }

    public void threadPoolCrawler() {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            pool.execute(this::crawler);
        }

        pool.shutdown();

        while (true) {
            try {
                if (pool.awaitTermination(80000, TimeUnit.MILLISECONDS)) {

                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void crawler() {
        SpiderContextThreadLocalStore.set(spiderContext);
        while (true) {
            Request request = scheduler.poll();

            if (request == null) {
                System.out.println(Thread.currentThread() + "没有任务 爬虫结束");
                break;
            }

            downloader.download(request);
        }
    }

    public void startRequests(List<Request> requests) {

        requests.forEach(request -> scheduler.push(request));
    }
}
