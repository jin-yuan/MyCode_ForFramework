package com.sofb.crawler.framwork.example;

import com.sofb.crawler.framework.core.Spider;
import com.sofb.crawler.framework.core.model.PlatformPayLoad;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;

import java.util.Arrays;

/**
 * @author liuxuejun
 * @date 2019-11-13 11:04
 */
public class CrawlerWithCookie {

    private static Response processQfang(Response response) {
        System.out.println(response.getText());
        System.out.println("现在处理..." + response.getUrl() + response.getText().substring(0, 500));
        return response;
    }

    public static void main(String[] args) {
        PlatformPayLoad platformPayLoad = new PlatformPayLoad();
        platformPayLoad.setAsyncRun(false);
        platformPayLoad.setAbleKeepAlive(true);
        platformPayLoad.setThreadNum(1);
        platformPayLoad.addCallBack("processQfang", CrawlerWithCookie::processQfang);
        Request requestEntity =
                GenRequestFromUrl.addRequest(
                        "https://app.api.ke.com/Rentplat/v1/house/ext?house_code=SZ2372468056866684928&request_ts=1577773085", "city", "normal", "processQfang");
        requestEntity.addCookie("acw_sc__v2", "5dffa7fd9b4baba911e23c665f746a0af9e3eb81");
        requestEntity.addHeader("Host", "app.api.ke.com");
        requestEntity.addHeader("Cookie", "lianjia_uuid=30C9B46D-BC01-48E4-92ED-01917611872F; lianjia_ssid=CCB8E1DA-CA5D-49C9-9C76-34D55420D945; lianjia_udid=62307C20-13A0-45A8-818C-F6BCF035B370, lianjia_uuid=30C9B46D-BC01-48E4-92ED-01917611872F; lianjia_ssid=CCB8E1DA-CA5D-49C9-9C76-34D55420D945; lianjia_udid=62307C20-13A0-45A8-818C-F6BCF035B370; app_api_ke_servers=750a3149239cda491e5f1935d85704e5; lianjia_uuid=19a5646c-2158-411b-be60-44d26adc2f78; lianjia_ssid=56e40bb6-53d0-4826-b6b9-914236b20d56");
        requestEntity.addHeader("User-Agent", "Beike 2.23.0;iPhone8,1;iOS 12.4.1;");
        requestEntity.addHeader("Referer", "matrix%2FrentalList");
        requestEntity.addHeader("Lianjia-Device-Id", "62307C20-13A0-45A8-818C-F6BCF035B370");
        requestEntity.addHeader("Page-Schema", "matrix%2FrentalDetail");
        requestEntity.addHeader("Lianjia-Version", "2.23.0");
        requestEntity.addHeader("Connection", "keep-alive");
        requestEntity.addHeader("Authorization", "MjAxODAxMTFfaW9zOjk3YzNkNWU1ODJmYjdhOWNkNGJlOWIzNTI2YmU1ZWM5OWI1YTk1OGU=");
        requestEntity.addHeader("Lianjia-Im-Version", "1");
        requestEntity.addHeader("Lianjia-Recommend-Allowable", "1");
        requestEntity.addHeader("wifi_name", "sofb");
        requestEntity.addHeader("Device-Info", "scale=2.0;screenwidth=750;screenheight=1334");
        requestEntity.addHeader("Accept-Language", "zh-Hans-CN;q=1");
        requestEntity.addHeader("Accept", "*/*");
        requestEntity.addHeader("Lianjia-City-Id", "440300");
        requestEntity.addHeader("Accept-Encoding", "br, gzip, deflate");
        requestEntity.addHeader("extension", "lj_idfa=935BB4D9-2F79-49E9-B85D-6A733A03E80D&lj_idfv=26A81DBF-CA27-4356-A55C-31113A298008&lj_device_id_ios=30C9B46D-BC01-48E4-92ED-01917611872F&lj_keychain_id=62307C20-13A0-45A8-818C-F6BCF035B370&lj_duid=D2jWg8tR9+SUwuITRan/1N3aEks2eYPESiXn79RBiTsHYX5a");
        requestEntity.addHeader("Cache-Control", "no-cache");
        requestEntity.addHeader("Postman-Token", "1e1a8207-1dcb-490d-82e3-3aee63fdf810,17d99f67-e93b-43ea-94da-8753344e23d9");
        requestEntity.addHeader("cache-control", "no-cache");
        platformPayLoad.setStartRequest(Arrays.asList(requestEntity));
        Spider.runWithPlatformPayLoad(platformPayLoad);
    }
}
