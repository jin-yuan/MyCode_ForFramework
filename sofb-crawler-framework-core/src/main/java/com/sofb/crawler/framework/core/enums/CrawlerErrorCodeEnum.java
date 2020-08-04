package com.sofb.crawler.framework.core.enums;

import com.sofb.crawler.framework.core.exception.ErrorCode;

/**
 * @author liuxuejun
 * @date 2019-12-20 16:39
 */
public enum CrawlerErrorCodeEnum implements ErrorCode {
    /**
     * 启动错误
     */
    CRAWLER_INIT_PARAMS_NULL("100", "启动错误"),
    /**
     * 解析错误
     */
    PROCESS_RESPONSE_ERROR("200", "解析异常"),
    /**
     * 反爬错误
     */
    ANTI_CRAWLER_ERROR("300", "反爬错误"),

    DOWNLOAD_ERROR("400","下载错误"),

    UNSPECIFIED("500", "未定义异常");

    /**
     * 错误码
     */
    private String code;

    /**
     * 状态描述
     */
    private String desc;

    CrawlerErrorCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
}
