package com.sofb.crawler.framework.core.util;

import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpResponseEntity;
import com.sofb.crwaler.framework.common.net.httpclient.executor.GenericDownloadExecutor;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

class GenRequestFromUrlTest {

    @Test
    void addRequest() {

        Request request =
                GenRequestFromUrl.addRequest(
                        "https://sz.centanet.com/cntapi/400agent/?t=1574129198541", "broker", "", "");

        request.setMethod("POST");
        request.setParams(
                new HashMap<String, Object>() {
                    {
                        put("Param[0][id]", "a6819acf-0473-cea8-5a37-08d75825facd");
                        put("Param[0][staffno]", "ppsz00020325");
                        put("Type", "POST");
                    }
                });
        try {
            HttpResponseEntity responseEntity = GenericDownloadExecutor.downloadExecutor(request);
            System.out.println(responseEntity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}