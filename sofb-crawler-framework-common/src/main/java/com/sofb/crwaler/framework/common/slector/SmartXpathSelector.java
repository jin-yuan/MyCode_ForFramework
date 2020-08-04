package com.sofb.crwaler.framework.common.slector;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.XPatherException;
import org.seimicrawler.xpath.JXDocument;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liuxuejun
 * @date 2020-01-13 11:39
 **/
public class SmartXpathSelector {


    public static List<String> xpath(String content, String pattern) {

        return jsoupXpath(content, pattern);
//        try {
//            if (pattern.contains("contain")) {
//                return jsoupXpath(content, pattern);
//            }
//            return cleanerXpath(content, pattern);
//        } catch (XPatherException e) {
//            return jsoupXpath(content, pattern);
//
//        }
    }


    private static List<String> jsoupXpath(String content, String pattern) {
        return JXDocument.create(content).sel(pattern).stream().map(Object::toString).collect(Collectors.toList());

    }


    private static List<String> cleanerXpath(String content, String pattern) throws XPatherException {

        return Arrays.stream(new HtmlCleaner().clean(content).evaluateXPath(pattern)).map(Object::toString).collect(Collectors.toList());
    }
}
