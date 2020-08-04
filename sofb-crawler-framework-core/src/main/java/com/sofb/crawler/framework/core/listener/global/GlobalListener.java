package com.sofb.crawler.framework.core.listener.global;

import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-12-19 20:21
 */
public interface GlobalListener {

    /**
     * 初始化重新获取任务
     */
    void beforeInit();


    /**
     * 拿到任务初始情况
     *
     * @param request 请求
     */
    void afterInit(Request request);

    /**
     * 成功时处理方法
     *
     * @param request  request
     * @param response response
     */
    void onSuccess(Request request, Response response);

    /**
     * 出错时，可以处理方法
     *
     * @param request  request
     * @param response response
     * @param e        异常
     */
    void onError(Request request, Response response, CrawlerException e);

    /**
     * 出错时，无法处理方法
     *
     * @param request  request
     * @param response response
     * @param e        异常
     */
    void unCatch(Request request, Response response, CrawlerException e);
}
