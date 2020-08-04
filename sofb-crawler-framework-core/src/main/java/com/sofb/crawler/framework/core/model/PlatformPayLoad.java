package com.sofb.crawler.framework.core.model;

import com.sofb.crawler.framework.core.Downloader;
import com.sofb.crawler.framework.core.Pipeline;
import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.download.HttpClientDownload;
import com.sofb.crawler.framework.core.listener.callback.CallBackManager;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListener;
import com.sofb.crawler.framework.core.listener.downloader.DownloadListenerManager;
import com.sofb.crawler.framework.core.listener.global.DefaultGlobalListener;
import com.sofb.crawler.framework.core.listener.global.GlobalListener;
import com.sofb.crawler.framework.core.listener.global.GlobalListenerManager;
import com.sofb.crawler.framework.core.pipeline.PipeLineManager;
import com.sofb.crawler.framework.core.schedule.StandAloneScheduler;
import com.sofb.crawler.framework.core.util.GenRequestFromUrl;
import com.sofb.crawler.framework.core.util.constant.CrawlerConstantParams;
import com.sofb.crawler.framework.core.util.constant.RequestConstant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liuxuejun
 * @date 2019-10-29 15:23
 * 用于在爬取开始的定制化操作
 */
@Data
public class PlatformPayLoad {

    /**
     * 项目名字
     */
    String projectName;

    /**
     * 城市
     */
    String city = "SHENZHEN";


    /**
     * 项目组别任务
     */
    private List<String> consumeTypes = Arrays.asList("new", "update", "all", "fast");
    /**
     * 下载器
     */
    private Downloader downloader = new HttpClientDownload();

    private PipeLineManager pipeLineManager = new PipeLineManager();

    private CallBackManager callBackManager = new CallBackManager();

    private GlobalListenerManager globalListenerManager = new GlobalListenerManager();

    private DownloadListenerManager downloadListenerManager = new DownloadListenerManager();

    /**
     * 调度器
     */
    private Scheduler schedule = new StandAloneScheduler();

    /**
     * 爬虫应用名
     */
    private String platformName;
    /**
     * 统一队列前缀
     */
    private String taskQueuePrefix = RequestConstant.PREFIX;
    /**
     * taskQueue Suffix 爬虫队列后缀，定义优先级用
     */
    private List<String> taskQueueSuffix = Arrays.asList("online", "normal", "old", "test");
    /**
     * startUrls 爬虫启动初始化url
     */
    private List<Request> startRequest = new ArrayList<>();
    /**
     * threadNum 初始化线程个数
     */
    private int threadNum = 1;
    /**
     * startTime 爬虫启动时间，方便按照时间处理过期爬虫
     */
    private Long startTime = CrawlerConstantParams.START_TIME;

    /**
     * 是否是异步启动
     */
    private boolean asyncRun = false;

    private boolean ableKeepAlive = false;

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

    /**
     * 使用代理概率
     */
    private double useProxyRate = 0;

    /**
     * 自定义环境变量
     */
    private Map<String, String> extension;

    public void initDefaultSetting() {
        globalListenerManager.addListener(new DefaultGlobalListener(this.getSchedule(), 5));
    }


    public void addRequest(Request request) {
        startRequest.add(request);
    }

    /**
     * @param requestType      任务类型
     * @param url              请求url
     * @param consumeType      消费类型
     * @param queue            队列
     * @param callBackFunction 回调函数
     */
    public void addRequest(
            String requestType, String url, String consumeType, String queue, String callBackFunction) {
        startRequest.add(
                GenRequestFromUrl.addRequest(url, requestType, consumeType, queue, callBackFunction));
    }

    public void addRequest(String requestType, String url, String consumeType, String queue, Map<String, Object> extras) {
        startRequest.add(GenRequestFromUrl.addRequest(url, requestType, consumeType, queue, "defaultCallBack", extras));
    }

    public void addRequest(String requestType, String url, String consumeType, String queue) {
        startRequest.add(
                GenRequestFromUrl.addRequest(url, requestType, consumeType, queue, "defaultCallBack"));
    }

    /**
     * @param requestType      任务类型
     * @param url              链接
     * @param callBackFunction 回调函数
     */
    public void addRequest(String requestType, String url, String callBackFunction) {
        startRequest.add(GenRequestFromUrl.addRequest(url, requestType, callBackFunction));
    }


    public void addRequest(String requestType, String url) {
        startRequest.add(GenRequestFromUrl.addRequest(url, requestType, "defaultCallBack"));
    }


    /**
     * 增加pipeline 单个
     *
     * @param pipeline 单个pipeline
     */
    public void addPipeLine(Pipeline pipeline) {
        pipeLineManager.addListener(pipeline);
    }

    /**
     * 增加pipeline 列表
     *
     * @param pipelineList pipeLine列表
     */
    public void addPipeLine(List<Pipeline> pipelineList) {
        pipeLineManager.addListener(pipelineList);
    }

    /**
     * 增加下载监听器
     *
     * @param downloadListeners 监听器列表
     */
    public void addDownLoadListener(List<DownloadListener> downloadListeners) {
        downloadListenerManager.addListener(downloadListeners);
    }

    /**
     * 增加全局监听器
     *
     * @param globalListeners 监听器列表
     */
    public void addGlobalListener(List<GlobalListener> globalListeners) {
        globalListenerManager.addListener(globalListeners);
    }


    /**
     * 增加回调函数
     *
     * @param functionMap 回调函数map
     */
    public void addCallBack(Map<String, CallBackFunction> functionMap) {
        callBackManager.addCallBack(functionMap);
    }

    /**
     * 增加回调函数
     *
     * @param name             函数名
     * @param callBackFunction 回调函数map
     */
    public void addCallBack(String name, CallBackFunction callBackFunction) {
        callBackManager.addCallBack(name, callBackFunction);
    }

    public String genQueue(String consumeType, String suffix) {
        return StringUtils.join(
                Arrays.asList(
                        this.getTaskQueuePrefix(),
                        this.getProjectName(),
                        this.getPlatformName(),
                        consumeType,
                        suffix),
                ":");

    }
}
