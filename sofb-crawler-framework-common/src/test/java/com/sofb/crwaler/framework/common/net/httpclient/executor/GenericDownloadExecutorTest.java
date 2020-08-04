package com.sofb.crwaler.framework.common.net.httpclient.executor;

import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.junit.jupiter.api.Test;
import org.seimicrawler.xpath.JXDocument;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

class GenericDownloadExecutorTest {


    @Test
    void api() {
        HttpRequestEntity requestEntity = new HttpRequestEntity();
//        requestEntity.setParams(new HashMap<String, Object>(){{
//            put("city_id","440300");
//            put("condition","zrn0");
//            put("is_second_filter",0);
//            put("limit",30);
//            put("offset",0);
//            put("request_ts",1577412396);
//            put("scene","list");
//            put("tdsourcetag","s_pctim_aiomsg");
//        }});
        requestEntity.setUrl(
                "https://app.api.ke.com/Rentplat/v2/house/list");
        //            requestEntity.setCharset("gb2312");
        requestEntity.setMethod("GET");
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            System.out.println(responseEntity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void jsoupSpeed() throws IOException {


        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl(
                "https://sz.centanet.com/ershoufang/g5/");
        //            requestEntity.setCharset("gb2312");
        requestEntity.setMethod("GET");

        HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);

        try {
            long tm0 = System.currentTimeMillis();
            TagNode tagNode = new HtmlCleaner().clean(responseEntity.getText());
            long tm1 = System.currentTimeMillis();
            System.out.println(
                    Arrays.stream(tagNode.evaluateXPath("//h4[@class=\"house-title\"]/a/@href")).map(Object::toString).collect(Collectors.toList()));
            long tm2 = System.currentTimeMillis();
            System.out.println((tm2 - tm1) + "  dddddddddddd " + (tm1 - tm0));
        } catch (XPatherException e) {
            e.printStackTrace();
        }

    }


    @Test
    void xpathSpeed() {
        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl(
                "https://sz.centanet.com/ershoufang/g5/");
        //            requestEntity.setCharset("gb2312");
        requestEntity.setMethod("GET");
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            long tm0 = System.currentTimeMillis();
            JXDocument jxDocument = JXDocument.create(responseEntity.getText());
            long tm1 = System.currentTimeMillis();
            System.out.println(
                    jxDocument.sel("//h4[@class=\"house-title\"]/a/@href"));
            long tm2 = System.currentTimeMillis();
            System.out.println((tm2 - tm1) + "  dddddddddddd " + (tm1 - tm0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void downloadExecutor() {

        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl(
                "https://sz.centanet.com/cntapi/ershoufang/agentcomment?pageIndex=1&postId=c60c82a4-5567-cf56-510d-08d7724f4a1d&adsno=szhqc0010659298&postType=S");
        //            requestEntity.setCharset("gb2312");
        requestEntity.setMethod("GET");
        requestEntity.addHeader(
                "referer",
                "https://sz.centanet.com/cntapi/ershoufang/agentcomment?pageIndex=1&postId=c60c82a4-5567-cf56-510d-08d7724f4a1d&adsno=szhqc0010659298&postType=S");
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            System.out.println(responseEntity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void beikeTest() {

        HttpRequestEntity requestEntity = new HttpRequestEntity();
        requestEntity.setUrl("https://app.api.ke.com/Rentplat/v1/house/ext?house_code=SZ2372468056866684928&request_ts=1577773085");
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
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            System.out.println(responseEntity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    void zhongyuanBrokerPostTest() {
        HttpRequestEntity requestEntity = new HttpRequestEntity();

        requestEntity.setUrl("https://sz.centanet.com/cntapi/400agent/?t=1574129198541");
        requestEntity.setParams(
                new HashMap<String, Object>() {
                    {
                        put("Param[0][id]", "a6819acf-0473-cea8-5a37-08d75825facd");
                        put("Param[0][staffno]", "ppsz00020325");
                        put("Type", "post");
                    }
                });
        requestEntity.setHeaders(
                new HashMap<String, String>() {
                    {
                        put("referer", "https://sz.centanet.com/ershoufang/szqhzm0010024582.html");
                    }
                });
        requestEntity.setMethod("POST");
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(requestEntity);
            System.out.println(responseEntity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
