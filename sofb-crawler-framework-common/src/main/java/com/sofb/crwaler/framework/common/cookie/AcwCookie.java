package com.sofb.crwaler.framework.common.cookie;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import com.sofb.crwaler.framework.common.net.httpclient.executor.GenericDownloadExecutor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

import static com.sofb.crwaler.framework.common.util.SofbStringUtils.getMatchesGroup1Text;

/**
 * @author liuxuejun
 * @date 2019-10-11 10:16
 */
public class AcwCookie implements ICookie {
    public static final String acwCookieKey = "acw_sc__v2";
    private static final String EMPTY_STRING = StringUtils.EMPTY;
    private static final Map<String, String> EMPTY_MAP = new HashMap<>();
    private static final int ARRAYS_SIZE = 40;
    private static final String NORMAL_KEY = "body";

    private static final String key = "3000176000856006061501533003690027800375";

    private static String getAcwCookie(String content) {
        String arg1 = getMatchesGroup1Text("arg1='([^']+)'", content, EMPTY_STRING);
        String key1 = unsBox(arg1);

        return hexXor(key, key1);
    }

    private static String unsBox(String arg) {

        List<Integer> arg1 =
                new ArrayList<>(
                        Arrays.asList(
                                0xf, 0x23, 0x1d, 0x18, 0x21, 0x10, 0x1, 0x26, 0xa, 0x9, 0x13, 0x1f, 0x28, 0x1b,
                                0x16, 0x17, 0x19, 0xd, 0x6, 0xb, 0x27, 0x12, 0x14, 0x8, 0xe, 0x15, 0x20, 0x1a, 0x2,
                                0x1e, 0x7, 0x4, 0x11, 0x5, 0x3, 0x1c, 0x22, 0x25, 0xc, 0x24));
        List<String> arg2 = new ArrayList<>();
        String result;
        for (int i = 0; i < ARRAYS_SIZE; i++) {
            arg2.add(EMPTY_STRING);
        }
        for (int i = 0; i < arg.length(); i++) {
            String arg3 = arg.substring(i, i + 1);
            for (int j = 0; j < arg1.size(); j++) {
                if (arg1.get(j) == i + 0x1) {
                    arg2.set(j, arg3);
                }
            }
        }

        result = StringUtils.join(arg2, EMPTY_STRING);
        return result;
    }

    private static String hexXor(String item1, String item2) {
        String item3 = EMPTY_STRING;
        int item4 = 0x0;
        while (item4 < item2.length() && item4 < item1.length()) {
            int item5 = Integer.parseInt(item2.substring(item4, item4 + 0x2), 16);
            int item6 = Integer.parseInt(item1.substring(item4, item4 + 0x2), 16);
            String item7 = "0x" + Integer.toHexString(item5 ^ item6);
            if (item7.equals(0x1 + EMPTY_STRING)) {
                item7 = "\\x30" + item7;
            }
            String item = item7.substring(2);
            if (item.length() == 1) {
                item = "0" + item;
            }
            item3 += item;
            item4 += 0x2;
        }

        return item3;
    }

    public static Map<String, String> checkCookieIsAvailable(HttpRequestEntity requestEntity) {
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            if (responseEntity.getText().contains(NORMAL_KEY)) {
                return responseEntity.getCookies();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EMPTY_MAP;
    }

    public static void main(String[] args) {
        System.out.println(
                new AcwCookie()
                        .fetchCookie("https://shenzhen.qfang.com/sale/100667810?insource=sale_list&top=1"));
    }

    public Map<String, String> fetchCookie(String url) {
        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl(url);
        return fetchCookie(requestEntity);
    }

    @Override
    public Map<String, String> fetchCookie(HttpRequestEntity requestEntity) {
        Map<String, String> cookieMap = new HashMap<>(8);
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            String content = responseEntity.getText();
            if (StringUtils.isNotEmpty(content) && content.contains(acwCookieKey)) {
                String acw = getAcwCookie(content);
                requestEntity.addCookie("acw_sc__v2", acw);
                requestEntity.addCookie("language", "SIMPLIFIED");
                cookieMap.putAll(requestEntity.getCookies());
                cookieMap.putAll(checkCookieIsAvailable(requestEntity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cookieMap;
    }

    @Override
    public Map<String, String> parseCookie(String text) {
        Map<String, String> cookieMap = new HashMap<>(8);
        if (text.contains(acwCookieKey)) {
            String acw = getAcwCookie(text);
            cookieMap.put("acw_sc__v2", acw);
            cookieMap.put("language", "SIMPLIFIED");
        }

        return cookieMap;
    }
}
