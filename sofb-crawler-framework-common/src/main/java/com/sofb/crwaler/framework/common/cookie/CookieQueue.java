package com.sofb.crwaler.framework.common.cookie;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author liuxuejun
 * @date 2019-10-11 12:49
 */
public class CookieQueue {

    //最大计数
    private int maxCount;

    private ICookie iCookie;

    private HttpRequestEntity requestEntity;

    private String keyCookieName;

    private ConcurrentLinkedDeque<Map<String, String>> cookieDeque = new ConcurrentLinkedDeque<>();

    public CookieQueue(CookieQueueConfig cookieQueueConfig) {
        this.maxCount = cookieQueueConfig.getMaxCount();
        this.iCookie = cookieQueueConfig.getICookie();
        this.requestEntity = cookieQueueConfig.getHttpRequestEntity();
        this.keyCookieName = cookieQueueConfig.getKeyCookieName();
    }

    public int pushCookie() {
        checkIsFull();
        requestEntity.clearCookie();
        Map<String, String> cookies = iCookie.fetchCookie(requestEntity);
        return pushCookie(cookies);
    }

    public int genCookie(String text) {
        return pushCookie(iCookie.parseCookie(text));
    }

    public int pushCookie(Map<String, String> cookies) {
        if (StringUtils.isEmpty(keyCookieName) || cookies.containsKey(keyCookieName)) {
            cookieDeque.push(cookies);
        }

        return getSize();
    }

    private void checkIsFull() {
        if (cookieDeque.size() >= maxCount) {
            cookieDeque.pollFirst();
        }
    }

    public int getSize() {
        return cookieDeque.size();
    }

    public Map<String, String> peekCookie() {
        if (cookieDeque.isEmpty()) {
            pushCookie();
        }
        return cookieDeque.peekFirst();
    }
}
