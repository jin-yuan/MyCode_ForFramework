package com.sofb.crawler.framework.core.mock;

import com.sofb.crawler.framework.core.Scheduler;
import com.sofb.crawler.framework.core.model.Request;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author liuxuejun
 * @date 2019-11-11 15:05
 */
public class MockSchedule implements Scheduler {
    private LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        MockSchedule mockSchedule = new MockSchedule();

        //    mockSchedule.push(Request.builder().url("url 1111111111").build());
        //    mockSchedule.push(Request.builder().url("url 222222222222").build());
        //    mockSchedule.push(Request.builder().url("url 333333333333").build());
        //    mockSchedule.push(Request.builder().url("url 444444444").build());
        //    mockSchedule.push(Request.builder().url("url 55555555555").build());

        for (; ; ) {

            System.out.println(mockSchedule.poll());
        }
    }

    @Override
    public void push(Request request) {
        queue.add(request);
    }

    @Override
    public Request poll() {

        try {
            return queue.poll(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


}
