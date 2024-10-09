package com.omar.async.requests;

import com.omar.async.AsyncProfilerRequest;

import static com.omar.async.AsyncProfileActions.START;

public class StartCPUAsyncProfilerRequest extends AsyncProfilerRequest {

    private final long duration;

    public StartCPUAsyncProfilerRequest(long duration, String event, String outputFile) {
        super(event, START.name(), outputFile);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

}