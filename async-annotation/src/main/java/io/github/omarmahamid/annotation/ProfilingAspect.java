package io.github.omarmahamid.annotation;

import io.github.omarmahamid.async.AsyncProfilerRequest;
import io.github.omarmahamid.async.IAsyncProfilerHandler;
import io.github.omarmahamid.async.ModeAsyncProfiler;
import io.github.omarmahamid.async.ModeAsyncProfilerFactory;
import io.github.omarmahamid.async.requests.StartAsyncProfilerRequest;
import io.github.omarmahamid.async.requests.StopAsyncProfilerRequest;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProfilingAspect {


    @Pointcut("execution(public static void main(String[])) && @within(Profiling)")
    public void mainMethod() {
    }

    @Before("mainMethod()")
    public void beforeMain() {
        System.out.println("Starting profiling...");
        ModeAsyncProfiler modeAsyncProfiler = ModeAsyncProfiler.CPU;
        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(modeAsyncProfiler);
        AsyncProfilerRequest request = new StartAsyncProfilerRequest(10L, "cpu", "/Users/omarmahamid/Documents/GitHub/async/async-annotation/profiling.html");
        try {
            profilerHandler.handle(request);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After("mainMethod()")
    public void afterMain() {
        System.out.println("Finishing profiling...");
        ModeAsyncProfiler modeAsyncProfiler = ModeAsyncProfiler.CPU;
        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(modeAsyncProfiler);
        AsyncProfilerRequest request = new StopAsyncProfilerRequest( "cpu", "/Users/omarmahamid/Documents/GitHub/async/async-annotation/profiling.html");
        try {
            profilerHandler.handle(request);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
