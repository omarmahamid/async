package com.omar.async;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncProfilerScheduler {



    public AsyncProfilerScheduler(ScheduledExecutorService executorService,
                                  IAsyncProfilerHandler profilerHandler,
                                  String event,
                                  long duration){

        executorService.scheduleAtFixedRate(
                new AsyncProfilerThread(profilerHandler, event, duration),
                0,
                duration,
                TimeUnit.SECONDS
        );
    }

}
