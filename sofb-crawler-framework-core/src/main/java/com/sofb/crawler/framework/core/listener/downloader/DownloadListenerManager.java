package com.sofb.crawler.framework.core.listener.downloader;

import com.sofb.crawler.framework.core.listener.BaseListenerManager;
import com.sofb.crawler.framework.core.model.Request;
import com.sofb.crawler.framework.core.model.Response;

/**
 * @author liuxuejun
 * @date 2019-12-19 19:35
 */
public class DownloadListenerManager extends BaseListenerManager<DownloadListener>
        implements DownloadListener {

    @Override
    public void preDownload(Request request) {
        forEach(m -> m.preDownload(request));
    }

    @Override
    public void afterDownload(Response response) {
        forEach(m -> m.afterDownload(response));
    }
}
