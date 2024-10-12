package com.omar.async;

import one.profiler.AsyncProfiler;

import java.io.IOException;

public class AbstractProfilerHandler {

    protected final AsyncProfiler profiler;

    protected AbstractProfilerHandler(AsyncProfiler profiler) {
        this.profiler = profiler;
    }

    protected void execute(String command) throws IOException {
        profiler.execute(command);
    }
}
