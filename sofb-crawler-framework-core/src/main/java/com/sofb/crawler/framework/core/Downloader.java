package com.sofb.crawler.framework.core;

import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;

import java.io.IOException;

/**
 * 下载网页
 *
 * @author liuxuejun
 * @date 2019-10-16 16:34
 */
public interface Downloader {

    /**
     * 下载页面核心接口
     *
     * @param request 请求体
     * @return response 响应体
     */
    Response download(Request request);
}
