package com.sofb.crawler.framwork.example;

import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;

import java.util.Arrays;

public class CrawlerWithPost {

    private static Response processGuotuju(Response response) {
        System.out.println(response.getText());
        System.out.println("现在处理..." + response.getUrl() + response.getText().substring(0, 500));
        return response;
    }

    public static void main(String[] args) {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.setAsyncRun(false);
        platformPayLoad.setAbleKeepAlive(true);
        platformPayLoad.setThreadNum(1);
        platformPayLoad.addCallBack("processGuotuju", CrawlerWithPost::processGuotuju);
        Request requestEntity =
                GenRequestFromUrl.addRequest(
                        "http://zjj.sz.gov.cn/ris/bol/szfdc/index.aspx", "city", "normal", "processGuotuju");
        requestEntity.setMethod("POST");
        requestEntity.addParam("scriptManager2", "updatepanel2|AspNetPager1");
        requestEntity.addParam("__EVENTTARGET", "AspNetPager1");
        requestEntity.addParam("__EVENTARGUMENT", "2");
        requestEntity.addParam("__VIEWSTATEGENERATOR", "2A35A6B2");
        requestEntity.addParam("ddlPageCount", "10");
        requestEntity.addParam("__EVENTVALIDATION", "4ZofzhblSTJ97/Qmw3+QZuak6U3O7vwqxiwpf6cZbv6NBPnaGntpbTl80Bkwk6iLUOB0UiagBJfDfGAzWB0qLAR+CAd/AAWlebhX3x5sS72qvqj7qJW5N3ItI61J+yjOQv1Ksci7GhquyyfjOtD82AkV/8GW/JLPbEGEKOwzwN/Ro6a/07cMlcCbQj8nicURu3GCFZFsKdx5kZVIuP044Yun9/I=");
        requestEntity.addParam("__VIEWSTATE", "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");
        platformPayLoad.setStartRequest(Arrays.asList(requestEntity));
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }
}


