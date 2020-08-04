package com.sofb.crawler.framework.core.listener;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author liuxuejun
 * @date 2019-12-20 10:05
 */
public class BaseListenerManager<T> {
    private List<T> listenerList = new ArrayList<>();

    public void addListener(T listener) {
        listenerList.add(listener);
    }

    public List<T> getAllListener() {
        return listenerList;
    }

    public void addListener(List<T> listeners) {
        if (CollectionUtils.isNotEmpty(listeners)) {
            listenerList.addAll(listeners);
        }

    }


    public void forEach(Consumer<? super T> action) {
        List<T> allListener = getAllListener();
        if (CollectionUtils.isNotEmpty(allListener)) {
            allListener.forEach(action);
        }

    }
}
