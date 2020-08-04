package com.sofb.crawler.framework.core.listener.downloader;

import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-12-19 19:25
 */
public interface DownloadListener {

    /**
     * 下载前检查
     *
     * @param request 包装request
     */
    void preDownload(Request request);

    /**
     * 下载后检查response
     *
     * @param response 检查response
     */
    void afterDownload(Response response);
}
