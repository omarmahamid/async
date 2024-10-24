package io.github.omarmahamid.annotation;

import io.github.omarmahamid.async.IAsyncProfilerHandler;
import io.github.omarmahamid.async.ModeAsyncProfiler;
import io.github.omarmahamid.async.ModeAsyncProfilerFactory;
import io.github.omarmahamid.async.shutdown.ShutdownHookAsyncProfiling;

import java.lang.reflect.Method;

public class ProfilingProcessor {


    private void process(Class<?> clazz) {

        try {
            Method mainMethod = clazz.getDeclaredMethod("main", String[].class);

            if (mainMethod != null) {
                mainMethod.setAccessible(true);
            }

            ModeAsyncProfiler mode;
            if (mainMethod.isAnnotationPresent(Profiling.class)) {
                Profiling profiling = mainMethod.getAnnotation(Profiling.class);
                mode = profiling.mode();
            }else{

            }

            IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(mode);

        }catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(
                new Thread(new ShutdownHookAsyncProfiling())
        );



    }


}
