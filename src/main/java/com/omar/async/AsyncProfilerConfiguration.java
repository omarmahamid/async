package com.omar.async;


import com.omar.async.exception.EventNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
public class AsyncProfilerConfiguration {



    @Bean
    public AsyncProfilerScheduler createScheduler(@Value("async-profiler-event") String event,
                                                  @Value("async-profiler-duration") long duration){

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, new AsyncProfilerThreadFactory());

        ModeAsyncProfiler modeAsyncProfiler = getModeAsyncProfiler(event);

        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(modeAsyncProfiler);

        return new AsyncProfilerScheduler(executorService, profilerHandler, event, duration);

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
