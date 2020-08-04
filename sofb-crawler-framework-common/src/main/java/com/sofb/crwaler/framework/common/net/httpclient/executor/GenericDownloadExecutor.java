package com.sofb.crwaler.framework.common.net.httpclient.executor;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpClientRequestContext;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import com.sofb.crwaler.framework.common.net.httpclient.manager.CloseableClients;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liuxuejun
 * @date 2019-10-09 14:24
 */
@Slf4j
public class GenericDownloadExecutor {

    private static CloseableHttpClient closeableHttpClient =
            CloseableClients.getDefaultCloseableClient();

    public static HttpResponseEntity downloadExecutor(HttpRequestEntity requestEntity)
            throws IOException {

        HttpClientRequestContext requestContext = HttpUriRequestConverter.convert(requestEntity);

        return downloadExecutor(
                requestContext.getHttpUriRequest(), requestContext.getHttpClientContext());
    }

    /**
     * 所有请求最基础方法
     *
     * @return httpResponse
     */
    private static HttpResponseEntity downloadExecutor(
            HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException {

        String charset =
                Optional.ofNullable(httpContext.getAttribute("charset")).orElse("utf8").toString();

        // 利用官方推荐的responseHandler方式处理回调，好处是不用关闭连接
        ResponseHandler<HttpResponseEntity> responseHandler =
                (response) -> {
                    HttpResponseEntity httpResponse = new HttpResponseEntity();
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                        HttpEntity entity = response.getEntity();
                        httpResponse.setContent(entity.getContent());
                        httpResponse.setText(EntityUtils.toString(entity, charset));
                        httpResponse.setHeaders(response.getAllHeaders());
                        httpResponse.setCookies(parseResponseCookie(response.getHeaders("Set-Cookie")));
                    }
                    httpResponse.setStatus(status);
                    httpResponse.setCharset(charset);
                    httpResponse.setUrl(httpUriRequest.getURI().toURL().toString());
                    return httpResponse;
                };

        return closeableHttpClient.execute(httpUriRequest, responseHandler, httpContext);
    }

    private static Map<String, String> parseResponseCookie(Header[] cookieString) {
        return Arrays.stream(cookieString)
                .map(Header::getValue)
                .map(m -> m.split(";")[0])
                .filter(StringUtils::isNotEmpty)
                .map(m -> m.split("="))
                .filter(m -> m.length == 2)
                .collect(Collectors.toMap(m -> m[0], m -> m[1], (a, b) -> b));
    }
}
