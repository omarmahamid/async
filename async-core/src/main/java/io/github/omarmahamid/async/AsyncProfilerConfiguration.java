package io.github.omarmahamid.async;



import io.github.omarmahamid.async.exception.EventNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@ConditionalOnProperty(name = "async-profiler-enabled", havingValue = "true", matchIfMissing = true)
public class AsyncProfilerConfiguration {


    /**
     * Creates an instance of {@link AsyncProfilerScheduler} with the provided settings.
     *
     * @param event        The type of profiling to perform (e.g. cpu, alloc, wall).
     * @param duration     The duration for which profiling should be performed.
     * @param baseOutputFile The base output file for profiling results.
     * @return An instance of {@link AsyncProfilerScheduler}.
     */
    @Bean
    public AsyncProfilerScheduler createScheduler(@Value("${async-profiler-event:cpu}") String event,
                                                  @Value("${async-profiler-duration:1000}") long duration,
                                                  @Value("${async-profiler-basedir}") String baseOutputFile){

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2, new AsyncProfilerThreadFactory());

        ModeAsyncProfiler modeAsyncProfiler = getModeAsyncProfiler(event);

        IAsyncProfilerHandler profilerHandler = ModeAsyncProfilerFactory.getAsyncProfiler(modeAsyncProfiler);

        return new AsyncProfilerScheduler(executorService, profilerHandler, event, duration, baseOutputFile);

    }

    /**
     * Returns an instance of {@link ModeAsyncProfiler} based on the provided event type.
     *
     * @param event The type of profiling to perform (e.g. cpu, alloc, wall).
     * @return An instance of {@link ModeAsyncProfiler}.
     */
    private ModeAsyncProfiler getModeAsyncProfiler(String event){
        switch (event){
            case "cpu" -> {
                return ModeAsyncProfiler.CPU;
            }
            case "alloc" -> {
                return ModeAsyncProfiler.ALLOC;
            }
            case "wall" -> {
                return ModeAsyncProfiler.WALL;
            }
            default -> throw new EventNotSupportedException(String.format("Event %s not supported", event));
        }
    }

}
