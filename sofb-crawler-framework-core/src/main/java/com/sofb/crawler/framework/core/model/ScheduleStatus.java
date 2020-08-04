package com.sofb.crawler.framework.core.model;

import lombok.Data;

/**
 * @author liuxuejun
 * @date 2020-01-03 16:09
 **/
@Data
public class ScheduleStatus {

    private String targetQueue = "";

    private int continueTaskCount = 0;
}
