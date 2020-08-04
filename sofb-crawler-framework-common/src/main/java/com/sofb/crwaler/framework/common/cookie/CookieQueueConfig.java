package com.sofb.crwaler.framework.common.cookie;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import lombok.Builder;
import lombok.Data;

/**
 * @author liuxuejun
 * @date 2019-10-11 13:02
 */
@Data
@Builder
public class CookieQueueConfig {

    private ICookie iCookie;

    private int maxCount;

    private String testUrl;

    private HttpRequestEntity httpRequestEntity;

    private String keyCookieName;

    public HttpRequestEntity getHttpRequestEntity() {

        if (httpRequestEntity == null) {
            HttpRequestEntity requestEntity = new HttpRequestEntity();
            requestEntity.setUrl(testUrl);
            return requestEntity;
        }
        return httpRequestEntity;
    }
}
