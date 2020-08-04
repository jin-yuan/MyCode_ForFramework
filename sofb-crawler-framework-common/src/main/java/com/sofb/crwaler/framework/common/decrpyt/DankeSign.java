package com.sofb.crwaler.framework.common.decrpyt;

import org.apache.commons.codec.digest.DigestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuxuejun
 * @date 2019-09-11 16:19
 **/
public class DankeSign {

    private static String SAlt = "R2UJE2UNhINnvjfaVPEC7FM8aUBoRVmgtv0zAhAD";


    private static String encryptParam(Map<String, String> params) {
        String sign = params.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(item -> item.getKey() + item.getValue()).collect(Collectors.joining());
        return DigestUtils.md5Hex(SAlt + sign + SAlt).toUpperCase();

    }

    public static Map<String, String> getSign(String url) {
        Map<String, String> result = new HashMap<>(5);

        try {

            Map<String, String> params = Arrays.stream(new URL(url).getQuery().split("&")).collect(Collectors.toMap(u -> u.split("=")[0], u -> u.split("=")[1]));
            params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            String sign = encryptParam(params);
            result.put("sign", sign);
            result.put("url", url.split("\\?")[0] + "?" + params.entrySet().stream().map(i -> i.getKey() + "=" + i.getValue()).collect(Collectors.joining("&")));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return result;

    }


    public static void main(String[] args) {
        System.out.println(DankeSign.getSign("https://api-room.danke.com/v1/room/detail?id=1296605465&rent_type=2&test_source=b&timestamp=1568187609"));
    }


}
