package com.sofb.crawler.framework.core;

import com.sofb.crawler.framework.core.model.ResultItems;

/**
 * 保存页面解析的结果
 *
 * @author liuxuejun
 * @date 2019-10-16 17:53
 */
public interface Pipeline<T> {
    /**
     * 存储解析后的结果
     *
     * @param resultItems 解析items
     */
    void process(ResultItems<T> resultItems);
}
