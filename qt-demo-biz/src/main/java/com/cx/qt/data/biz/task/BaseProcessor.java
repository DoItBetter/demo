package com.cx.qt.data.biz.task;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;

public abstract class BaseProcessor extends JavaProcessor {
    private volatile boolean stop = false;

    @Override
    public void kill(JobContext context) {
        stop = true;
    }
}
