package com.sofb.crawler.framework.core.util;

import com.sofb.crawler.framework.core.model.Request;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.sofb.crawler.framework.core.util.constant.RequestConstant.TASK_TYPE;

/**
 * @author liuxuejun
 * @date 2019-12-17 10:41
 */
public class StringUtil {

    public static String genDataId(Request request) {
        return DigestUtils.sha1Hex(request.getUrl() + request.getExtra(TASK_TYPE).toString());
    }

    public static String genDataId(String url, String urlType) {
        return DigestUtils.sha1Hex(url + urlType + System.currentTimeMillis());
    }

    public static String genQueue(String... item) {

        return StringUtils.join(
                Arrays.stream(item).map(String::trim).collect(Collectors.toList()), ":");
    }
}
