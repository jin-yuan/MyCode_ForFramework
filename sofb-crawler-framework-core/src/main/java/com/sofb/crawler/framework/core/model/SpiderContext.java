package com.sofb.crawler.framework.core.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-17 19:46
 */
@Data
public class SpiderContext {

    /**
     * 任务队列前缀
     */
    String prefix;
    /**
     * 工程名
     */
    private String projectName;

    /**
     * 使用代理概率
     */
    private double useProxyRate = 0;
    /**
     * 爬虫应用名
     */
    private String platformName;
    /**
     * 城市
     */
    private String city;
    private List<String> queueList;
    /**
     * 每页个数
     */
    private int perPageCount;
    /**
     * 调度阻塞时间
     */
    private boolean ableKeepAlive;
    private int scheduleBlockTime = 100;
    /**
     * 包含任务列类型
     */
    private String[] includeTypes;
    /**
     * 剔除任务类型
     */
    private String[] excludeTypes;
    /**
     * 包含表类型
     */
    private String[] includeTables;
    /**
     * 剔除任务列表
     */
    private String[] excludeTables;

    private Map<String, String> extension;
    //    private String consumeType;

}
