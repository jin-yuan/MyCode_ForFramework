package com.sofb.crwaler.framework.common.net.httpclient.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2020-01-02 18:03
 **/
public class IpUtils {

    public static String hostName() {
        InetAddress addr;
        String hostname = "Unknown";
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }


    public static String userName() {

        Map<String, String> map = System.getenv();
        // 获取用户名
        return map.getOrDefault("USERNAME", "Unknown");
    }
}
