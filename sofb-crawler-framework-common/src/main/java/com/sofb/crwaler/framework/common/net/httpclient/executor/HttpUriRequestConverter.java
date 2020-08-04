package com.sofb.crwaler.framework.common.net.httpclient.executor;

import com.sofb.crwaler.framework.common.net.httpclient.constant.HttpConstant;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpClientRequestContext;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.Proxy;
import com.sofb.crwaler.framework.common.net.httpclient.util.UrlUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-09 15:26
 */
public class HttpUriRequestConverter {

    public static HttpClientRequestContext convert(HttpRequestEntity requestEntity) {

        HttpClientRequestContext httpClientRequestContext = new HttpClientRequestContext();
        httpClientRequestContext.setHttpUriRequest(convertHttpUriRequest(requestEntity));
        httpClientRequestContext.setHttpClientContext(convertHttpClientContext(requestEntity));
        return httpClientRequestContext;
    }

    private static HttpClientContext convertHttpClientContext(HttpRequestEntity requestEntity) {
        HttpClientContext httpContext = new HttpClientContext();
        httpContext.setAttribute("charset", requestEntity.getCharset());
        if (requestEntity.getCookies() != null && !requestEntity.getCookies().isEmpty()) {
            CookieStore cookieStore = new BasicCookieStore();
            for (Map.Entry<String, String> cookieEntry : requestEntity.getCookies().entrySet()) {
                BasicClientCookie cookie1 =
                        new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie1.setDomain(UrlUtils.removePort(UrlUtils.getDomain(requestEntity.getUrl())));
                cookieStore.addCookie(cookie1);
            }
            httpContext.setCookieStore(cookieStore);
        }
        Proxy proxy = requestEntity.getProxy();
        if (proxy != null && proxy.getUsername() != null) {
            AuthState authState = new AuthState();
            authState.update(new BasicScheme(ChallengeState.PROXY), new UsernamePasswordCredentials(proxy.getUsername(), proxy.getPassword()));
            httpContext.setAttribute(HttpClientContext.PROXY_AUTH_STATE, authState);
        }
        return httpContext;
    }

    private static HttpUriRequest convertHttpUriRequest(HttpRequestEntity requestEntity) {

        RequestBuilder requestBuilder =
                selectRequestMethod(requestEntity)
                        .setUri(UrlUtils.fixIllegalCharacterInUrl(requestEntity.getUrl()));

        if (MapUtils.isNotEmpty(requestEntity.getHeaders())) {
            for (Map.Entry<String, String> headerEntry : requestEntity.getHeaders().entrySet()) {
                requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        Proxy proxy = requestEntity.getProxy();
        if (proxy != null) {
            requestConfigBuilder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }
        requestConfigBuilder.setConnectionRequestTimeout(requestEntity.getTimeOut() * 1000)
                .setSocketTimeout(requestEntity.getTimeOut() * 1000)
                .setConnectTimeout(requestEntity.getTimeOut() * 1000).setRedirectsEnabled(true)
                .setCookieSpec(CookieSpecs.STANDARD);


        requestBuilder.setConfig(requestConfigBuilder.build());
        return requestBuilder.build();
    }

    private static RequestBuilder selectRequestMethod(HttpRequestEntity requestEntity) {
        String method = requestEntity.getMethod();
        if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
            // default get
            return RequestBuilder.get();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
            return addFormParams(RequestBuilder.post(), requestEntity);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
            return RequestBuilder.head();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
            return addFormParams(RequestBuilder.put(), requestEntity);
        } else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
            return RequestBuilder.delete();
        } else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
            return RequestBuilder.trace();
        }
        throw new IllegalArgumentException("Illegal HTTP Method " + method);
    }

    /**
     * @// TODO: 2019/10/9 暂不兼容post
     */
    private static RequestBuilder addFormParams(
            RequestBuilder requestBuilder, HttpRequestEntity requestEntity) {

        if (requestEntity.getParams() != null) {
            Map<String, Object> params = requestEntity.getParams();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairs.add(
                        new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            try {
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
                requestBuilder.setEntity(formEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return requestBuilder;
    }
}
