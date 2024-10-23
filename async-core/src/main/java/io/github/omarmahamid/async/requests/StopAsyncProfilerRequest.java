package io.github.omarmahamid.async.requests;

import io.github.omarmahamid.async.AsyncProfilerRequest;

import static io.github.omarmahamid.async.AsyncProfileActions.STOP;

public class StopAsyncProfilerRequest extends AsyncProfilerRequest {


    public StopAsyncProfilerRequest(String event, String outputFile) {
        super(event, STOP.name(), outputFile);
    }
}
