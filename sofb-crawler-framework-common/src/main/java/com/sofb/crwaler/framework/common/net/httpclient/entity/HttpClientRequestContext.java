package com.sofb.crwaler.framework.common.net.httpclient.entity;

import lombok.Data;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * 增加HttpClientRequestContext 仅仅暴露covert方法
 *
 * @author liuxuejun
 * @date 2019-10-09 15:24
 */
@Data
public class HttpClientRequestContext {

    private HttpUriRequest httpUriRequest;

    private HttpClientContext httpClientContext;
}
