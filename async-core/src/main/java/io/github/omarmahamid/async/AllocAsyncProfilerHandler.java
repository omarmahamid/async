package io.github.omarmahamid.async;


import io.github.omarmahamid.async.exception.ActionNotSupportedException;
import io.github.omarmahamid.async.exception.AsyncProfilerException;
import one.profiler.AsyncProfiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Handles async profiling requests for allocation tracking.
 */
public class AllocAsyncProfilerHandler extends AbstractProfilerHandler implements IAsyncProfilerHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(AllocAsyncProfilerHandler.class);


    /**
     * Initializes the alloc async profiler handler with the provided async profiler.
     *
     * @param asyncProfiler The async profiler to use for profiling.
     */
    AllocAsyncProfilerHandler(AsyncProfiler asyncProfiler) {
        super(asyncProfiler);
    }

    /**
     * Handles an async profiling request.
     *
     * @param request  The async profiling request to handle.
     * @throws io.github.omarmahamid.async.exception.AsyncProfilerException If an error occurs during profiling.
     */
    @Override
    public void handle(AsyncProfilerRequest request) throws AsyncProfilerException {

        String action = request.getAction();
        String outputFile = request.getOutputFile();

        LOGGER.info("Handling async CPU profiler {}, action {}", request.getEvent(), action);
        try {
            if (AsyncProfileActions.START.name().equals(action)) {
                execute(String.format("start,event=alloc,file=%s", outputFile));
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
