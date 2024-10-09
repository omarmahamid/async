package com.omar.async;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncProfilerThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public Thread newThread(Runnable r) {
        int counted = counter.getAndIncrement();
        return new Thread(r, "omar-async-profiling" + counted);
    }
}
