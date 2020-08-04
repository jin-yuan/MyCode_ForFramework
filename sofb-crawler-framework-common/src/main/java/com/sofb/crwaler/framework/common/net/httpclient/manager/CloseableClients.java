package com.sofb.crwaler.framework.common.net.httpclient.manager;

import com.sofb.crwaler.framework.common.net.httpclient.interceptor.DefaultHeaderInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxuejun
 * @date 2019-10-09 10:43
 */
public class CloseableClients {

    private static ConnectionManager defaultConnectionManager = new ConnectionManager();

    /**
     * 返回clientBuilder
     */
    private static HttpClientBuilder getClientBuilder(
            PoolingHttpClientConnectionManager httpClientConnectionManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        httpClientBuilder.addInterceptorFirst(new DefaultHeaderInterceptor());

        httpClientBuilder.setConnectionTimeToLive(20, TimeUnit.SECONDS);

        httpClientBuilder.setRedirectStrategy(new SimpleRedirectStrategy());
        return httpClientBuilder;
    }

    /**
     * 对外返回CloseableClient
     */
    public static CloseableHttpClient getDefaultCloseableClient() {

        return getClientBuilder(defaultConnectionManager.getDefaultConnectionManager()).build();
    }
}

@Slf4j
class SimpleRedirectStrategy extends LaxRedirectStrategy {

    @Override
    public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context)
            throws ProtocolException {
        URI uri = getLocationURI(request, response, context);
        String method = request.getRequestLine().getMethod();
        if ("post".equalsIgnoreCase(method)) {
            try {
                HttpRequestWrapper httpRequestWrapper = (HttpRequestWrapper) request;
                httpRequestWrapper.setURI(uri);
                httpRequestWrapper.removeHeaders("Content-Length");
                return httpRequestWrapper;
            } catch (Exception e) {
                log.error("强转为HttpRequestWrapper出错");
            }
            return new HttpPost(uri);
        } else {
            return new HttpGet(uri);
        }
    }
}
