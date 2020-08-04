package com.sofb.crawler.framework.core.listener.callback;

import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.CallBackFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2019-12-25 14:38
 */
public class CallBackManager {

    private Map<String, CallBackFunction> callBackFunctionMap = new HashMap<>();

    public CallBackFunction getFunction(String funcName) {
        return Optional.ofNullable(callBackFunctionMap.get(funcName))
                .orElseThrow(() -> new CrawlerException(CrawlerErrorCodeEnum.CRAWLER_INIT_PARAMS_NULL));
    }

    public void addCallBack(String funcName, CallBackFunction callBackFunction) {
        callBackFunctionMap.put(funcName, callBackFunction);
    }

    public void addCallBack(Map<String, CallBackFunction> functionMap) {
        callBackFunctionMap.putAll(functionMap);
    }
}
