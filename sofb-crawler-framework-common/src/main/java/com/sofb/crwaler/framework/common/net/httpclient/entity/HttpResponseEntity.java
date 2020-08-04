package com.sofb.crwaler.framework.common.net.httpclient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.Header;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;

/**
 * httpResponse 响应实体类
 *
 * @author liuxuejun
 * @date 2019-10-09 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseEntity implements Serializable {

    private static final long serialVersionUID = -3765193920362778554L;
    /**
     * 响应列表
     */
    private String url;

    /**
     * 响应状态码
     */
    private int status;

    /**
     * 响应文本内容
     */
    private String text;

    /**
     * 响应编码
     */
    private String charset;

    /**
     * 响应流内容
     */
    private InputStream content;

    /**
     * 响应headers
     */
    private Header[] headers;

    /**
     * 响应cookie
     */
    private Map<String, String> cookies;
}
