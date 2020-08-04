package com.sofb.crawler.framework.core.mock;

import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.SpiderContext;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author liuxuejun
 * @date 2019-11-11 16:15
 */
public class MockSpiderTest {

    public List<Request> fetchRequests() {
        //    return Arrays.asList(
        //        Request.builder().url("url 11111111111").build(),
        //        Request.builder().url("url 222222222222222").build(),
        //        Request.builder().url("url 333333333333").build(),
        //        Request.builder().url("url 44444444444").build(),
        //        Request.builder().url("url 55555555555555").build(),
        //        Request.builder().url("url 66666666666").build(),
        //        Request.builder().url("url 77777777777777777").build(),
        //        Request.builder().url("url 88888888888888").build(),
        //        Request.builder().url("url 9999999999999").build(),
        //        Request.builder().url("url 10101010101010").build(),
        //        Request.builder().url("url 1111111111111111111").build(),
        //        Request.builder().url("url 12121212121212121212").build());
        //  }
        return null;
    }

    @Test
    public void singleThreadSpiderStop() {
        SpiderContext spiderContext = new SpiderContext();
        MockMultiThreadSpider mockMultiThreadSpider = new MockMultiThreadSpider();
        mockMultiThreadSpider.setSpiderContext(spiderContext);
        mockMultiThreadSpider.startRequests(fetchRequests());
        mockMultiThreadSpider.crawler();
    }

    @Test
    public void singleThreadSpiderKeepAlive() {
        SpiderContext spiderContext = new SpiderContext();
        MockMultiThreadSpider mockMultiThreadSpider = new MockMultiThreadSpider();
        mockMultiThreadSpider.setSpiderContext(spiderContext);
        mockMultiThreadSpider.startRequests(fetchRequests());
        mockMultiThreadSpider.start();
    }

    @Test
    public void threadPoolSpider() {
        SpiderContext spiderContext = new SpiderContext();
        MockMultiThreadSpider mockMultiThreadSpider = new MockMultiThreadSpider();
        mockMultiThreadSpider.setSpiderContext(spiderContext);
        mockMultiThreadSpider.startRequests(fetchRequests());
        mockMultiThreadSpider.setAsyncRun(false);
        mockMultiThreadSpider.start();
    }

    @Test
    public void singleThreadAsync() {
        SpiderContext spiderContext = new SpiderContext();
        spiderContext.setScheduleBlockTime(-1);
        MockMultiThreadSpider mockMultiThreadSpider = new MockMultiThreadSpider();
        mockMultiThreadSpider.setSpiderContext(spiderContext);
        mockMultiThreadSpider.startRequests(fetchRequests());
        mockMultiThreadSpider.setAsyncRun(true);
        mockMultiThreadSpider.start();

        try {
            System.out.println("异步执行");
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
