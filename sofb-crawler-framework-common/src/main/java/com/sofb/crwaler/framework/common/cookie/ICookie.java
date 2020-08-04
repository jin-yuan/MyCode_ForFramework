package com.sofb.crwaler.framework.common.cookie;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;

import java.util.Map;

public interface ICookie {

    /**
     * 通过目标url获取cookie*
     */
    Map<String, String> fetchCookie(HttpRequestEntity requestEntity);

    //解析Cookie
    Map<String, String> parseCookie(String text);
}
