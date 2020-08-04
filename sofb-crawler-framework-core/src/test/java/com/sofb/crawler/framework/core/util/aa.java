package com.sofb.crawler.framework.core.util;

import android.util.Base64;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author liuxuejun
 * @date 2020-01-15 15:02
 **/
public class aa {

    public static String getAuthorization(String str) {
        Map hashMap = getParmars(str);
        List arrayList = new ArrayList(hashMap.entrySet());
        Collections.sort(arrayList, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                return entry.getKey().compareTo(entry2.getKey());
            }
        });
        String GetAppSecret = "93273ef46a0b880faf4466c48f74878f";//JniClient.GetAppSecret(APPConfigHelper.c());
        String GetAppId = "20170324_android";//JniClient.GetAppId(APPConfigHelper.c());
        StringBuilder stringBuilder = new StringBuilder(GetAppSecret);
        for (int i = 0; i < arrayList.size(); i++) {
            Map.Entry entry = (Map.Entry) arrayList.get(i);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String) entry.getKey());
            stringBuilder2.append("=");
            stringBuilder2.append((String) entry.getValue());
            stringBuilder.append(stringBuilder2.toString());
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("sign origin=");

        stringBuilder3.append(stringBuilder);
        str = Digest_SHA1(stringBuilder.toString());
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(GetAppId);
        stringBuilder3.append(":");
        stringBuilder3.append(str);
        str = Base64.encodeToString(stringBuilder3.toString().getBytes(), 2);
        return str;
    }

    public static String Digest_SHA1(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                if (toHexString.length() < 2) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(toHexString);
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static Map<String, String> getParmars(String url) {
        Map<String, String> resMap = new HashMap<>();
        String[] split = url.split("\\?");
        if (split.length == 2) {
            String paramStr = split[1];
            String[] params = paramStr.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] param = params[i].split("=");
                if (param.length == 1) {
                    String key = param[0];
                    String value = null;
                    resMap.put(key, "");
                }
                if (param.length >= 2) {
                    String key = param[0];
                    String value = param[1];
                    for (int j = 2; j < param.length; j++) {
                        value += "=" + param[j];
                    }
                    resMap.put(key, value);
                }
            }
        }
        return resMap;
    }


    @Test
    public void parseIndex() throws InterruptedException {

        String detailUrl2 = "https://app.api.lianjia.com/house/resblock/detailpart1?id=2411048614076";
        String authorization = getAuthorization(detailUrl2);
        Map<String, String> map = new HashMap<>();
        System.out.println(authorization);


    }
}
