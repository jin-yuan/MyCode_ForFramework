package com.sofb.crawler.framework.core.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储item
 *
 * @author liuxuejun
 * @date 2019-10-16 17:56
 */
@Data
public class ResultItems<T> {
    Map<String, T> items = new HashMap<>();

    String msg;

    long batchId;

    boolean isInvalidPage;

    String consumeType;

    Map<String, String> extras;

    public void addItems(Map<String, T> newItem) {
        if (newItem != null) {
            items.putAll(newItem);
        }
    }
}
