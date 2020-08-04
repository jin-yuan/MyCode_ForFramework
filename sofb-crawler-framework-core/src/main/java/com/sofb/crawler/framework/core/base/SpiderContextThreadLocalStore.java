package com.sofb.crawler.framework.core.base;

import com.sofb.crawler.framework.core.model.SpiderContext;

/**
 * @author liuxuejun
 * @date 2019-11-08 14:51
 */
public class SpiderContextThreadLocalStore {

    private static ThreadLocal<SpiderContext> spiderContextThreadLocal = new ThreadLocal<>();

    public static void set(SpiderContext user) {
        spiderContextThreadLocal.set(user);
    }

    public static SpiderContext get() {
        SpiderContext spiderContext = spiderContextThreadLocal.get();
        return spiderContext == null ? new SpiderContext() : spiderContext;
    }

    public static void remove() {
        spiderContextThreadLocal.remove();
    }
}
