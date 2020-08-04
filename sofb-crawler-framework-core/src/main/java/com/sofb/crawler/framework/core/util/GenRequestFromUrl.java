package com.sofb.crawler.framework.core.util;

import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import com.sofb.crwaler.framework.common.util.SofbDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-12-17 10:40
 */
@Slf4j
public class GenRequestFromUrl {

    public static Request addRequest(
            String newUrl, String taskType, Response response, String callBackFunction) {
        return addRequest(newUrl, taskType, new HashMap<String, Object>(0), response, callBackFunction);
    }

    public static Request addRequest(
            String newUrl, String taskType, Map<String, Object> param, Response response) {
        return addRequest(newUrl, taskType, param, response, response.getCallBackName());
    }

    public static Request addRequest(
            String newUrl,
            String taskType,
            Map<String, Object> param,
            Response response,
            String callBackFunction) {

        if (StringUtils.isNotEmpty(newUrl)) {
            newUrl = newUrl.trim();
            try {
                if (!newUrl.startsWith("http")) {
                    newUrl = new URI(response.getUrl()).resolve(newUrl).toString();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        Request request =
                addRequest(
                        newUrl,
                        taskType,
                        response.getExtra(RequestConstant.CONSUME_TYPE).toString(),
                        response.getExtra(RequestConstant.QUEUE).toString(),
                        callBackFunction, response.getExtras());
        request.putExtra(param);
        return request;
    }

    public static Request addRequest(
            String newUrl, String taskType, String queue, String callBackFunction) {
        return addRequest(
                newUrl, taskType, RequestConstant.DEFAULT_CONSUME_TYPE, queue, callBackFunction);
    }

    public static Request addRequest(String newUrl, String taskType, String callBackFunction) {
        return addRequest(
                newUrl,
                taskType,
                RequestConstant.DEFAULT_CONSUME_TYPE,
                "no_target_queue",
                callBackFunction);
    }

    public static Request addRequest(
            String newUrl, String taskType, String consumeType, String queue, String callBackFunction, Map<String, Object> params) {
        Map<String, Object> baseExtras = new HashMap<>(8);
        baseExtras.putAll(params);
        Request request = new Request();
        baseExtras.put(RequestConstant.URL, newUrl);
        baseExtras.put(RequestConstant.TASK_TYPE, taskType);
        baseExtras.put(RequestConstant.ERROR_TIMES, 0);
        baseExtras.put(RequestConstant.START_TIME, Instant.now().getEpochSecond());
        baseExtras.put(RequestConstant.DATA_ID, StringUtil.genDataId(newUrl, taskType));
        baseExtras.put(RequestConstant.CONSUME_TYPE, consumeType);
        baseExtras.put(RequestConstant.QUEUE, queue);
        baseExtras.putIfAbsent(RequestConstant.BATCH_ID, SofbDateUtils.getNowFormatDate());
        request.setCallBackName(callBackFunction);
        request.setUrl(newUrl);
        request.setExtras(baseExtras);
        request.setAllowRedirects(true);
        return request;
    }

    public static Request addRequest(
            String newUrl, String taskType, String consumeType, String queue, String callBackFunction) {
        return addRequest(newUrl, taskType, consumeType, queue, callBackFunction, new HashMap<>());
    }
}
