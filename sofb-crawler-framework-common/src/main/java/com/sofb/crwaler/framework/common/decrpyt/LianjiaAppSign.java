package com.sofb.crwaler.framework.common.decrpyt;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author liuxuejun
 * @date 2020-02-04 20:57
 **/
public class LianjiaAppSign {

    public static String genSign(String urlStr) {
        return encripty(urlStr, null);

    }

    public static String encripty(String str, Map<String, String> map) {
        Map parmars = getParmars(str);

        HashMap hashMap = new HashMap();
        if (parmars != null) {
            hashMap.putAll(parmars);
        }
        if (map != null) {
            hashMap.putAll(map);
        }
        List arrayList = new ArrayList(hashMap.entrySet());
        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object obj, Object obj2) {
                return a((Map.Entry) obj, (Map.Entry) obj2);
            }

            public int a(Map.Entry<String, String> entry, Map.Entry<String, String> entry2) {
                return entry.getKey().compareTo(entry2.getKey());
            }
        });

        String GetAppSecret = "93273ef46a0b880faf4466c48f74878f";
        String GetAppId = "20170324_android";
        StringBuilder stringBuilder = new StringBuilder(GetAppSecret);
        for (int i = 0; i < arrayList.size(); i++) {
            Map.Entry entry = (Map.Entry) arrayList.get(i);
            stringBuilder.append(entry.getKey() + "=" + entry.getValue());
        }

        return Base64.encodeToString((GetAppId + ":" + Digest_SHA1(stringBuilder.toString())).getBytes(), 2);


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


    public static void main(String[] args) {
        //
        System.out.println(
                LianjiaAppSign.genSign("https://app.api.lianjia.com/house/resblock/detailpart2?id=2411048614076"));
    }
}
