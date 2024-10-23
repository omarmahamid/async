package io.github.omarmahamid.async;


import io.github.omarmahamid.async.exception.AsyncProfilerException;
import io.github.omarmahamid.async.requests.StartAsyncProfilerRequest;
import io.github.omarmahamid.async.requests.StopAsyncProfilerRequest;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A runnable that schedules async profiling tasks using the provided profiler handler.
 */
public class AsyncProfilerRunnable implements Runnable{

    private final IAsyncProfilerHandler profilerHandler;
    private final AtomicBoolean isStarted = new AtomicBoolean(false);
    private final String event;
    private final long duration;
    private String outputFile;
    private final String baseOutputFile;

    /**
     * Initializes the async profiler runnable with the provided profiler handler, event, duration, and base output file.
     *
     * @param profilerHandler The profiler handler to use for profiling.
     * @param event            The type of profiling to perform (e.g. cpu, alloc, wall).
     * @param duration         The duration for which profiling should be performed.
     */
    AsyncProfilerRunnable(IAsyncProfilerHandler profilerHandler,
                          String event,
                          long duration,
                          String baseOutputFile) {
        this.profilerHandler = profilerHandler;
        this.event = event;
        this.duration = duration;
        this.baseOutputFile = baseOutputFile;
    }

    /**
     * Runs the async profiling task by scheduling start and stop requests to the profiler handler.
     *
     * @throws RuntimeException If an error occurs during profiling.
     */
    @Override
    public void run() {

        AsyncProfilerRequest request;
        if (isStarted.get()) {
            request = new StopAsyncProfilerRequest(event, outputFile);
            try {
                profilerHandler.handle(request);
                return;
            } catch (AsyncProfilerException e) {
                throw new RuntimeException(e);
            }finally {
                isStarted.set(false);
            }
        }

        outputFile = String.format("%s/%s-%s", baseOutputFile, event, new Date());
        request = new StartAsyncProfilerRequest(duration, event, outputFile);
        try {
            profilerHandler.handle(request);
        } catch (AsyncProfilerException e) {
            throw new RuntimeException(e);
        }finally {
            isStarted.set(true);
        }
    }
}
