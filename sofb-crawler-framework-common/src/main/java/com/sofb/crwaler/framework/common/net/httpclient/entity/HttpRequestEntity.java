package com.sofb.crwaler.framework.common.net.httpclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * httpRequest 请求体封装的实体类
 *
 * @author liuxuejun
 * @date 2019-10-09 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestEntity implements Serializable {

    private static final long serialVersionUID = -7762102887277767884L;
    /**
     * 请求url
     */
    private String url;


    /**
     * 代理
     */
    private Proxy proxy;

    /**
     * 方法方法
     */
    private String method;

    /**
     * headers列表
     */
    private Map<String, String> headers = new HashMap<>();

    /**
     * cookie列表
     */
    private Map<String, String> cookies = new HashMap<>();

    /**
     * 请求参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 超时时间
     */
    private int timeOut = 10;

    /**
     * 重定向次数
     */
    private boolean allowRedirects = true;

    /**
     * 编码格式
     */
    private String charset;

    public void addCookie(String name, String value) {
        cookies.put(name, value);
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void clearCookie() {
        cookies.clear();
    }
}
