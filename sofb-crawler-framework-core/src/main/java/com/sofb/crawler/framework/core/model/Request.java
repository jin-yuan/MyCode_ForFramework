package com.sofb.crawler.framework.core.model;

import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import com.sofb.crwaler.framework.common.net.httpclient.entity.HttpRequestEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的request请求对象
 *
 * @author liuxuejun
 * @date 2019-10-16 17:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class Request extends HttpRequestEntity implements Serializable {

    private static final long serialVersionUID = -8438567436283160878L;

    private String callBackName;

    private Map<String, Object> extras = new HashMap<>();

    private Map<String, List<Map<String, String>>> items;

    public static boolean isValidRequest(Request request) {
        return request != null && StringUtils.isNotEmpty(request.getUrl());
    }

    public Object getExtra(String key) {
        return getExtra(key, StringUtils.EMPTY);
    }

    public Object getExtra(String key, Object defaultValue) {
        return extras.getOrDefault(key, defaultValue);
    }

    public void putExtra(String key, Object val) {
        extras.put(key, val);
    }

    public void putExtra(Map<String, Object> params) {
        extras.putAll(params);
    }


    public String fetchDataId() {
        return extras.getOrDefault(RequestConstant.DATA_ID, StringUtils.EMPTY).toString();

    }

    public String fetchQueue() {
        return extras.getOrDefault(RequestConstant.QUEUE, StringUtils.EMPTY).toString();
    }


    public boolean fetchUrgeStatus() {
        return StringUtils.isNotEmpty(extras.getOrDefault(RequestConstant.URGE, StringUtils.EMPTY).toString());
    }

    public String fetchBatchId() {

        return extras.getOrDefault(RequestConstant.BATCH_ID, StringUtils.EMPTY).toString();
    }


    public Long fetchStartTime() {

        return Long.parseLong(extras.getOrDefault(RequestConstant.START_TIME, "0").toString());

    }

    public void putStartTime(Long startTime) {

        extras.put(RequestConstant.START_TIME, startTime);
    }


}
