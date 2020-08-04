package com.sofb.crawler.framework.core;

import com.sofb.crawler.framework.core.model.Request;

/**
 * 任务调度接口
 *
 * @author liuxuejun
 * @date 2019-10-16 16:34
 */
public interface Scheduler {
    /**
     * 从队列中取任务
     *
     * @param request 请求实体类
     */
    void push(Request request);

    /**
     * 从队列取出任务
     *
     * @return request 请求实体类
     */
    Request poll();

}
