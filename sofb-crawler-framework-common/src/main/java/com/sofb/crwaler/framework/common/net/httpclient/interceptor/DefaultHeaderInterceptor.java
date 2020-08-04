package com.sofb.crwaler.framework.common.net.httpclient.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author liuxuejun
 * @date 2019-10-09 17:53
 */
public class DefaultHeaderInterceptor implements HttpRequestInterceptor {
    public static final String MOBILE_FLAG = "m";

    public void preExecutor(HttpRequest request) {
        String url = ((HttpRequestWrapper) request).getOriginal().getRequestLine().getUri();
        request.addHeader("Referer", url);

        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String host = uriBuilder.getHost();
        // 自动添加host header
        if (StringUtils.isNotEmpty(host)) {
            request.addHeader("Host", host);
        }
        // 根据是否是移动站切换,自动切换user-agent
        if (!request.containsHeader("User-Agent")) {
            if (url.trim().startsWith(MOBILE_FLAG)) {
                request.addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");

            } else {
                request.addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
            }
        }
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        request.addHeader("Accept", "*/*");
        request.addHeader("Pragma", "no-cache");
        request.addHeader("Accept-Encoding", "gzip, deflate,sdch");
        request.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        request.addHeader("Cache-Control", "no-cache");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        preExecutor(request);
    }
}
