package com.omar.async.requests;

import com.omar.async.AsyncProfilerRequest;

import static com.omar.async.AsyncProfileActions.STOP;

public class StopAsyncProfilerRequest extends AsyncProfilerRequest {


    public StopAsyncProfilerRequest(String event, String outputFile) {
        super(event, STOP.name(), outputFile);
    }
}
