package com.omar.async;

import com.omar.async.exception.AsyncProfilerException;
import com.omar.async.exception.EventNotSupportedException;
import one.profiler.AsyncProfiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllocAsyncProfilerHandler implements IAsyncProfilerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllocAsyncProfilerHandler.class);

    private final AsyncProfiler asyncProfiler;

    AllocAsyncProfilerHandler(AsyncProfiler asyncProfiler) {
        this.asyncProfiler = asyncProfiler;
    }

    @Override
    public void handle(AsyncProfilerRequest request) throws AsyncProfilerException {

        String action = request.getAction();
        String outputFile = request.getOutputFile();

        LOGGER.info("Handling async CPU profiler {}, action {}", request.getEvent(), action);
        try {
            if (AsyncProfileActions.START.name().equals(action)) {
                asyncProfiler.execute(String.format("start,event=alloc,file=%s", outputFile));
            } else if (AsyncProfileActions.STOP.name().equals(action)) {
                if (outputFile.contains(".html")) {
                    outputFile = outputFile.substring(0, outputFile.indexOf(".html"));
                }
                asyncProfiler.execute(String.format("stop,file=%s.html", outputFile));
            } else {
                throw new EventNotSupportedException(String.format("event %s not supported", request.getEvent()));
            }
        } catch (Exception e) {
            throw new AsyncProfilerException("Exception while profiling", e);
        }
    }

}
