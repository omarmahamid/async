package io.github.omarmahamid.async.requests;

import io.github.omarmahamid.async.AsyncProfilerRequest;

import static io.github.omarmahamid.async.AsyncProfileActions.START;

public class StartAsyncProfilerRequest extends AsyncProfilerRequest {

    private final long duration;

    public StartAsyncProfilerRequest(long duration, String event, String outputFile) {
        super(event, START.name(), outputFile);
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

}