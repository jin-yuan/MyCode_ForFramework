package com.sofb.crawler.framework.core.pipeline;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sofb.crawler.framework.core.Pipeline;
import com.sofb.crawler.framework.core.model.ResultItems;

import java.util.List;
import java.util.Map;

/**
 * 控制台pipeline
 *
 * @author liuxuejun
 * @date 2019-10-17 19:39
 */
public class ConsolePipeline implements Pipeline<List<Map<String, String>>> {
    @Override
    public void process(ResultItems<List<Map<String, String>>> resultItems) {
        System.out.println(
                JSON.toJSONString(resultItems, SerializerFeature.PrettyFormat));
    }
}
