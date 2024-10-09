package com.omar.async;


import com.omar.async.exception.EventNotSupportedException;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class AsyncProfilerConfiguration {



    public void createScheduler(){

        ExecutorService executorService = new ScheduledThreadPoolExecutor(2, new AsyncProfilerThreadFactory());


    }


    private ModeAsyncProfiler getModeAsyncProfiler(String event){
        switch (event){
            case "cpu" -> {
                return ModeAsyncProfiler.CPU;
            }
            case "alloc" -> {
                return ModeAsyncProfiler.ALLOC;
            }
            case "wall" -> {
                return ModeAsyncProfiler.WALL;
            }
            default -> throw new EventNotSupportedException(String.format("Event %s not supported", event));
        }
    }

}
