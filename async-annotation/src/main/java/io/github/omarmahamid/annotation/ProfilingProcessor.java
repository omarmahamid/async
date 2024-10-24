package io.github.omarmahamid.annotation;

import io.github.omarmahamid.annotation.exception.AnnotationNotProvidedException;
import io.github.omarmahamid.annotation.exception.FileDumpNotProvidedException;
import io.github.omarmahamid.async.AsyncProfilerRequest;
import io.github.omarmahamid.async.IAsyncProfilerHandler;
import io.github.omarmahamid.async.ModeAsyncProfiler;
import io.github.omarmahamid.async.ModeAsyncProfilerFactory;
import io.github.omarmahamid.async.requests.StartAsyncProfilerRequest;
import io.github.omarmahamid.async.requests.StopAsyncProfilerRequest;
import io.github.omarmahamid.async.shutdown.ShutdownHookAsyncProfiling;

import java.lang.reflect.Method;
import java.util.Optional;

public class ProfilingProcessor {


    public static void startProcess(Class<?> clazz) {
        Method mainMethod = getMainMethod(clazz);
        Profiling profiling = extractProfiling(mainMethod);
        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(profiling.mode());

        AsyncProfilerRequest request = new StartAsyncProfilerRequest(
                profiling.duration(), profiling.mode().name(), profiling.fileDump()
        );

        handleProfiling(profilerHandler, request);
        addShutdownHook(profiling.mode(), profiling.fileDump());
    }



    public static void stopProcess(Class<?> clazz) {
        Method mainMethod = getMainMethod(clazz);
        Profiling profiling = extractProfiling(mainMethod);
        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(profiling.mode());

        AsyncProfilerRequest request = new StopAsyncProfilerRequest(
                profiling.mode().name(), profiling.fileDump()
        );

        handleProfiling(profilerHandler, request);
    }


    private static Method getMainMethod(Class<?> clazz) {
        return Optional.ofNullable(getMethod(clazz, "main", String[].class))
                .orElseThrow(() -> new RuntimeException("Main method not found"));
    }

    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to retrieve method: " + methodName, e);
        }
    }

    private static Profiling extractProfiling(Method method) {

        Profiling profiling = Optional.ofNullable(method.getAnnotation(Profiling.class))
                .orElseThrow(() -> new AnnotationNotProvidedException("Profiling annotation not provided"));

        if (profiling.fileDump() == null || profiling.fileDump().isEmpty()){
            throw new FileDumpNotProvidedException("File dump not provided");
        }

        return profiling;
    }

    private static void handleProfiling(IAsyncProfilerHandler handler, AsyncProfilerRequest request) {
        try {
            handler.handle(request);
        } catch (Exception e) {
            throw new RuntimeException("Error during profiling execution", e);
        }
    }

    private static void addShutdownHook(ModeAsyncProfiler mode, String fileDump) {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookAsyncProfiling(mode, fileDump)));
    }
}
