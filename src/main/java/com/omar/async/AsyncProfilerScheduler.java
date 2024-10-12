package com.omar.async;

import com.omar.async.requests.StopAsyncProfilerRequest;
import org.springframework.beans.factory.DisposableBean;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncProfilerScheduler implements DisposableBean {

    private final String event;
    private final IAsyncProfilerHandler profilerHandler;
    private final String baseOutputFile;


    /**
     * Schedules async profiling tasks using the provided executor service.
     *
     * @param executorService The scheduled executor service to use for scheduling.
     * @param profilerHandler  The handler to use for profiling requests.
     * @param event            The type of profiling to perform (e.g. cpu, alloc, wall).
     * @param duration         The duration for which profiling should be performed.
     * @param baseOutputFile   The base output file for profiling results.
     */
    public AsyncProfilerScheduler(ScheduledExecutorService executorService,
                                  IAsyncProfilerHandler profilerHandler,
                                  String event,
                                  long duration,
                                  String baseOutputFile){

        this.profilerHandler = profilerHandler;
        this.event = event;
        this.baseOutputFile = baseOutputFile;
        executorService.scheduleAtFixedRate(
                new AsyncProfilerRunnable(profilerHandler, event, duration, baseOutputFile),
                0,
                duration,
                TimeUnit.SECONDS
        );
    }

    /**
     * When shut-down the application, stop the profiling gracefully.
     *
     * @throws Exception If an error occurs during destruction.
     */
    @Override
    public void destroy() throws Exception {
        String outputFile = String.format("%s/%s-%s", baseOutputFile, event, new Date());
        AsyncProfilerRequest request = new StopAsyncProfilerRequest(event, outputFile);
        profilerHandler.handle(request);
    }
}
