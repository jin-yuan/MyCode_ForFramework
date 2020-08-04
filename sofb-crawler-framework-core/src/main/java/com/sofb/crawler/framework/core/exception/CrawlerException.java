package com.sofb.crawler.framework.core.exception;

import com.sofb.crawler.framework.core.enums.CrawlerErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuxuejun
 * @date 2019-12-20 16:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CrawlerException extends RuntimeException {

    private static final long serialVersionUID = -7864604160297181941L;

    /**
     * 错误码
     */
    protected final ErrorCode errorCode;

    /**
     * 这个是和谐一些不必要的地方,冗余的字段 尽量不要用
     */
    private String code;

    /**
     * 无参默认构造UNSPECIFIED
     */
    public CrawlerException() {
        super(CrawlerErrorCodeEnum.CRAWLER_INIT_PARAMS_NULL.getDescription());
        this.errorCode = CrawlerErrorCodeEnum.UNSPECIFIED;
    }

    /**
     * 指定错误码构造通用异常
     *
     * @param errorCode 错误码
     */
    public CrawlerException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /**
     * 指定详细描述构造通用异常
     *
     * @param detailedMessage 详细描述
     */
    public CrawlerException(final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = CrawlerErrorCodeEnum.UNSPECIFIED;
    }

    /**
     * 指定导火索构造通用异常
     *
     * @param t 导火索
     */
    public CrawlerException(final Throwable t) {
        super(t);
        this.errorCode = CrawlerErrorCodeEnum.UNSPECIFIED;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     */
    public CrawlerException(final ErrorCode errorCode, final String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode 错误码
     * @param t         导火索
     */
    public CrawlerException(final ErrorCode errorCode, final Throwable t) {
        super(errorCode.getDescription(), t);
        this.errorCode = errorCode;
    }

    /**
     * 构造通用异常
     *
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public CrawlerException(final String detailedMessage, final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = CrawlerErrorCodeEnum.UNSPECIFIED;
    }

    /**
     * 构造通用异常
     *
     * @param errorCode       错误码
     * @param detailedMessage 详细描述
     * @param t               导火索
     */
    public CrawlerException(
            final CrawlerErrorCodeEnum errorCode, final String detailedMessage, final Throwable t) {
        super(detailedMessage, t);
        this.errorCode = errorCode;
    }

    /**
     * Getter method for property <tt>errorCode</tt>.
     *
     * @return property value of errorCode
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
