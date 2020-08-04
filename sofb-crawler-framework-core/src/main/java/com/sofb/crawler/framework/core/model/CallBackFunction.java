package com.sofb.crawler.framework.core.model;

import java.io.Serializable;

/**
 * @author liuxuejun
 * @date 2019-10-17 16:21
 */
@FunctionalInterface
public interface CallBackFunction extends Serializable {
    /**
     * 处理response回调函数
     *
     * @param response response
     */
    void call(Response response);
}
