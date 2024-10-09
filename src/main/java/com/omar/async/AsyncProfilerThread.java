package com.omar.async;

import com.omar.async.exception.AsyncProfilerException;
import com.omar.async.requests.StartCPUAsyncProfilerRequest;
import com.omar.async.requests.StopAsyncProfilerRequest;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncProfilerThread implements Runnable{

    private final IAsyncProfilerHandler profilerHandler;
    private final AtomicBoolean isStarted = new AtomicBoolean(false);
    private final String event;
    private final long duration;
    private String outputFile;

    AsyncProfilerThread(IAsyncProfilerHandler profilerHandler, String event, long duration){
        this.profilerHandler = profilerHandler;
        this.event = event;
        this.duration = duration;
    }


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
            }
        }

        outputFile = String.format("%s-%s", event, new Date());
        request = new StartCPUAsyncProfilerRequest(duration, event, outputFile);
        try {
            profilerHandler.handle(request);
        } catch (AsyncProfilerException e) {
            throw new RuntimeException(e);
        }finally {
            isStarted.set(true);
        }
    }
}
