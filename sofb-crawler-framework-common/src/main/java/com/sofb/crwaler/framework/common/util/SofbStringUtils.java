package com.sofb.crwaler.framework.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author liuxuejun
 * @date 2019-12-19 01:19
 */
public class SofbStringUtils {

    public static String getMatchesGroup1Text(String regex, String value, String defaultValue) {
        if (StringUtils.isEmpty(regex) || StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        Pattern r = Pattern.compile(regex);
        Matcher matcher = r.matcher(value);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return defaultValue;
    }

    public static String extractCommonSubString(String source, String compare) {
        StringBuilder mostCommonString = new StringBuilder();
        for (int i = 0; i < Math.min(source.length(), compare.length()); i++) {
            String suSource = source.substring(i, i + 1);
            String subCompare = compare.substring(i, i + 1);
            if (suSource.equals(subCompare)) {
                mostCommonString.append(suSource);
            } else {
                mostCommonString.append("%d");
                mostCommonString.append(source.substring(i + 1));
                break;
            }
        }

        return mostCommonString.toString();

    }


    public static List<String> genPageUrls(String baseUrl, int pageCount) {

        return IntStream.rangeClosed(1, pageCount)
                .boxed()
                .map(i -> String.format(baseUrl, i))
                .collect(Collectors.toList());

    }
}
