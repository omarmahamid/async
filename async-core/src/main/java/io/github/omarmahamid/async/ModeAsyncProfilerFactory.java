package io.github.omarmahamid.async;

import io.github.omarmahamid.async.exception.EventNotSupportedException;
import one.profiler.AsyncProfiler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModeAsyncProfilerFactory {

    private ModeAsyncProfilerFactory(){
        throw new IllegalStateException("Utility class");
    }

    private static final Map<ModeAsyncProfiler, IAsyncProfilerHandler> asyncProfilersModes = new ConcurrentHashMap<>();

    public static IAsyncProfilerHandler getAsyncProfiler(ModeAsyncProfiler modeAsyncProfiler){
        return asyncProfilersModes.computeIfAbsent(modeAsyncProfiler, key -> switch (key) {
            case CPU -> new CPUAsyncProfilerHandler(AsyncProfiler.getInstance());
            case ALLOC -> new AllocAsyncProfilerHandler(AsyncProfiler.getInstance());
            default -> throw new EventNotSupportedException(String.format("mode %s not supported currently", modeAsyncProfiler.name()));
        });
    }


}
