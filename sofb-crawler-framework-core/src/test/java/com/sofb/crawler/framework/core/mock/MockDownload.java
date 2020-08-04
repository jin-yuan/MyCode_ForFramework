package com.sofb.crawler.framework.core.mock;

import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-11-11 15:20
 */
public class MockDownload implements Downloader {
    @Override
    public Response download(Request request) {
        try {
            Thread.sleep(1000);
            System.out.println("download " + request.getUrl() + "success");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
