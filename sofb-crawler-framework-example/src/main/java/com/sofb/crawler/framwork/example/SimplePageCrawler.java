package com.sofb.crawler.framwork.example;

import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.CallBackFunction;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 最简单的爬虫
 *
 * @author liuxuejun
 * @date 2019-10-17 17:14
 */
public class SimplePageCrawler {

    private static Response processBaidu(Response response) {
        System.out.println("现在处理..." + response.getUrl());
        Request request = new Request();
        request.setUrl("https://www.sina.com");
        request.setCallBackName("processSina");
        response.addRequest(request);
        return response;
    }

    private static Response processSina(Response response) {

        System.out.println("现在处理..." + response.getUrl());

        return response;
    }

    public static void main(String[] args) {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.setAbleKeepAlive(false);
        platformPayLoad.setThreadNum(1);
        platformPayLoad.addCallBack(
                new HashMap<String, CallBackFunction>() {
                    {
                        put("processBaidu", SimplePageCrawler::processBaidu);
                        put("processSina", SimplePageCrawler::processSina);
                    }
                });
        platformPayLoad.setStartRequest(
                Arrays.asList(
                        GenRequestFromUrl.addRequest(
                                "https://www.baidu.com/index.php?tn=baidudg", "city", "normal", "processBaidu")));
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }
}
