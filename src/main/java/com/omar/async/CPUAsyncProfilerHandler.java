package com.omar.async;

import com.omar.async.exception.AsyncProfilerException;
import com.omar.async.exception.ActionNotSupportedException;
import one.profiler.AsyncProfiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles async profiling requests for cpu tracking.
 */
public class CPUAsyncProfilerHandler extends AbstractProfilerHandler implements IAsyncProfilerHandler{


    private static final Logger LOGGER = LoggerFactory.getLogger(CPUAsyncProfilerHandler.class);


    /**
     * Initializes the cpu async profiler handler with the provided async profiler.
     *
     * @param asyncProfiler The async profiler to use for profiling.
     */
    CPUAsyncProfilerHandler(AsyncProfiler asyncProfiler){
        super(asyncProfiler);
    }

    /**
     * Handles an async profiling request.
     *
     * @param request  The async profiling request to handle.
     * @throws AsyncProfilerException If an error occurs during profiling.
     */
    @Override
    public void handle(AsyncProfilerRequest request) throws AsyncProfilerException {

        String action = request.getAction();
        String outputFile = request.getOutputFile();

        LOGGER.info("Handling async CPU profiler {}, action {}", request.getEvent(), action);
        try {
            if (AsyncProfileActions.START.name().equals(action)) {
                execute(String.format("start,event=cpu,file=%s", outputFile));
            } else if (AsyncProfileActions.STOP.name().equals(action)) {
                if (outputFile.contains(".html")) {
                    outputFile = outputFile.substring(0, outputFile.indexOf(".html"));
                }
                execute(String.format("stop,file=%s.html", outputFile));
            } else {
                throw new ActionNotSupportedException(String.format("event %s not supported", request.getEvent()));
            }
        } catch (Exception e) {
            throw new AsyncProfilerException("Exception while profiling", e);
        }
    }
}
