package com.sofb.crawler.framework.core.download;

import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import org.apache.http.Consts;

import java.io.IOException;

/**
 * @author liuxuejun
 * @date 2019-10-17 19:08
 */
public class SimpleHttpClientDownLoad implements Downloader {

    @Override
    public Response download(Request request) {

        Response response = new Response();
        try {
            String text =
                    org.apache.http.client.fluent.Request.Get(request.getUrl())
                            .execute()
                            .returnContent()
                            .asString(Consts.UTF_8);
            response.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setUrl(request.getUrl());
        response.setCallBackName(request.getCallBackName());
        response.setExtras(request.getExtras());

        return response;
    }
}
