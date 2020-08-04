package com.sofb.crawler.framework.core.listener.global;

import com.sofb.crawler.framework.core.exception.CrawlerException;
import com.sofb.crawler.framework.core.listener.BaseListenerManager;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author liuxuejun
 * @date 2019-12-20 10:00
 */
@Slf4j
public class GlobalListenerManager extends BaseListenerManager<GlobalListener>
        implements GlobalListener {

    @Override
    public void beforeInit() {
        forEach(GlobalListener::beforeInit);
    }

    @Override
    public void afterInit(Request request) {
        forEach(m -> m.afterInit(request));
    }

    @Override
    public void onSuccess(Request request, Response response) {
        forEach(m -> m.onSuccess(request, response));
    }

    @Override
    public void onError(Request request, Response response, CrawlerException e) {
        try {
            forEach(m -> m.onError(request, response, e));
        } catch (RuntimeException e1) {
            unCatch(request, response, e);
        }


    }

    @Override
    public void unCatch(Request request, Response response, CrawlerException e) {
        try {
            forEach(m -> m.unCatch(request, response, e));
        } catch (RuntimeException e1) {
            log.error("发现未捕获异常{}，请求 {} 相应文本 {}", e1, request, Optional.ofNullable(response).orElse(new Response()).getText());
        }

    }
}
