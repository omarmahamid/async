package io.github.omarmahamid.async.shutdown;

import io.github.omarmahamid.async.AsyncProfilerRequest;
import io.github.omarmahamid.async.IAsyncProfilerHandler;
import io.github.omarmahamid.async.ModeAsyncProfiler;
import io.github.omarmahamid.async.ModeAsyncProfilerFactory;
import io.github.omarmahamid.async.requests.StopAsyncProfilerRequest;

public class ShutdownHookAsyncProfiling implements Runnable {

    private final ModeAsyncProfiler modeAsyncProfiler;
    private final String outputFile;

    public ShutdownHookAsyncProfiling(ModeAsyncProfiler modeAsyncProfiler, String outputFile) {
        this.modeAsyncProfiler = modeAsyncProfiler;
        this.outputFile = outputFile;
    }


    @Override
    public void run() {

        AsyncProfilerRequest stopRequest = new StopAsyncProfilerRequest(modeAsyncProfiler.name(), outputFile);

        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(modeAsyncProfiler);

        try {
            profilerHandler.handle(stopRequest);
        }catch (Exception e){
            throw new RuntimeException("Error while shutting down", e);
        }
    }
}
