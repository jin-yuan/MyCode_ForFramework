package com.sofb.crwaler.framework.common.net.httpclient.executor;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;

import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-09 14:24
 */
public class SimpleDownloadExecutor extends GenericDownloadExecutor {

    public static HttpResponseEntity get(String url) {

        return null;
    }

    public static HttpResponseEntity get(String url, Map<String, String> cookies) {

        return null;
    }

    public static HttpResponseEntity get(
            String url, Map<String, String> headers, Map<String, String> cookies) {

        return null;
    }

    public static HttpResponseEntity get(
            String url,
            Map<String, String> headers,
            Map<String, String> cookies,
            boolean allowRedirects) {
        return null;
    }
}
