package com.sofb.crwaler.framework.common.net.httpclient.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * proxy 代理实体类
 *
 * @author liuxuejun
 * @date 2019-10-09 10:38
 */
@Data
public class Proxy {

    private String host;
    private int port;
    private String username;
    private String password;

    private Date expireTime;

    public Proxy() {
    }


    public Proxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Proxy(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    private boolean isExpireTime() {

        if (expireTime != null) {

            return expireTime.after(new Date());
        }

        return true;
    }

    public boolean isInvalid() {

        return !(isExpireTime() || StringUtils.isEmpty(host));
    }
}
