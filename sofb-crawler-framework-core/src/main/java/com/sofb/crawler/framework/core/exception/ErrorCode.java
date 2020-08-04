package com.sofb.crawler.framework.core.exception;

/**
 * @author liuxuejun
 * @date 2019-12-20 16:59
 */
public interface ErrorCode {

    /**
     * 获取错误码
     *
     * @return 返回状态码
     */
    String getCode();

    /**
     * 获取错误信息
     *
     * @return 返回描述信息
     */
    String getDescription();
}
