package com.sofb.crawler.framework.core.download;

import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.model.ResultItems;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import com.sofb.crwaler.framework.common.net.httpclient.executor.GenericDownloadExecutor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

import java.io.IOException;

/**
 * 自定义httpclient 下载器处理
 *
 * @author liuxuejun
 * @date 2019-11-13 11:10
 */
@Slf4j
public class HttpClientDownload implements Downloader {
    @Override
    public Response download(Request request) {
        Response response = new Response();
        HttpResponseEntity responseEntity;
        try {
            responseEntity = GenericDownloadExecutor.downloadExecutor(request);
        } catch (IOException | RuntimeException e) {
            throw new CrawlerException(CrawlerErrorCodeEnum.DOWNLOAD_ERROR, e);

        }
        BeanCopier copier = BeanCopier.create(HttpResponseEntity.class, response.getClass(), false);
        copier.copy(responseEntity, response, null);

        response.setUrl(request.getUrl());
        response.setCallBackName(request.getCallBackName());
        response.setExtras(request.getExtras());
        ResultItems<java.util.List<java.util.Map<String, String>>> resultItems = new ResultItems<>();
        resultItems.addItems(request.getItems());
        response.setResultItems(resultItems);
        return response;
    }


}
