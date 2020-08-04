package com.sofb.crwaler.framework.common.net.httpclient.config;

import lombok.Data;

/**
 * @author liuxuejun
 * @date 2019-10-09 10:45
 */
@Data
public class PoolingHttpClientConnectionManagerConfig {

    private int maxTotal = 500;

    private int maxPerRoute = 50;
}
