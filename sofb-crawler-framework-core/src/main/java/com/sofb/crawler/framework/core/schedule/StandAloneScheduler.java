package com.sofb.crawler.framework.core.schedule;

import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 单机内存队列
 *
 * @author liuxuejun
 * @date 2019-10-17 16:57
 */
@Slf4j

public class StandAloneScheduler implements Scheduler {

    private final LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();


    @Override
    public void push(Request request) {

        queue.add(request);
    }

    @Override
    public Request poll() {
        try {

            return queue.poll(new Random().nextInt(100) + 100, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
