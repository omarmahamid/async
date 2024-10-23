package io.github.omarmahamid.async;

import com.omar.async.exception.AsyncProfilerException;
import com.omar.async.exception.ActionNotSupportedException;
import com.omar.async.requests.StartAsyncProfilerRequest;
import com.omar.async.requests.StopAsyncProfilerRequest;
import one.profiler.AsyncProfiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CPUAsyncProfilingHandlerTest {

    private AsyncProfiler asyncProfiler;
    private CPUAsyncProfilerHandler handler;

    @BeforeEach
    void setUp() {
        asyncProfiler = mock(AsyncProfiler.class);
        handler = new CPUAsyncProfilerHandler(asyncProfiler);
    }

    @Test
    void testHandleStartAction() throws AsyncProfilerException, IOException {
        AsyncProfilerRequest request = new StartAsyncProfilerRequest(10, "cpu", "out.html");

        handler.handle(request);

        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(asyncProfiler).execute(commandCaptor.capture());

        assertEquals("start,event=cpu,file=out.html", commandCaptor.getValue());
    }

    @Test
    void testHandleStopAction() throws AsyncProfilerException, IOException {
        AsyncProfilerRequest request = new StopAsyncProfilerRequest( "cpu", "out.html");

        handler.handle(request);

        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(asyncProfiler).execute(commandCaptor.capture());

        assertEquals("stop,file=out.html", commandCaptor.getValue());
    }

    @Test
    void testHandleStopActionWithoutHtmlExtension() throws AsyncProfilerException, IOException {
        AsyncProfilerRequest request = new StopAsyncProfilerRequest("cpu", "out");

        handler.handle(request);

        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(asyncProfiler).execute(commandCaptor.capture());

        assertEquals("stop,file=out.html", commandCaptor.getValue());
    }

    @Test
    void testHandleUnsupportedEvent() {
        AsyncProfilerRequest request = new AsyncProfilerRequest(null, "UNKNOWN_ACTION", "output") {
            @Override
            public String getAction() {
                return "UNKNOWN_ACTION";
            }
        };

        Exception exception = assertThrows(AsyncProfilerException.class, () -> handler.handle(request));

        assertTrue(exception.getCause() instanceof ActionNotSupportedException);

        assertEquals("event null not supported", exception.getCause().getMessage());
    }

    @Test
    void testHandleExecutionException() throws IOException {
        AsyncProfilerRequest request = new StartAsyncProfilerRequest(10, "cpu", "out");

        doThrow(new RuntimeException("Execution error")).when(asyncProfiler).execute(anyString());

        Exception exception = assertThrows(AsyncProfilerException.class, () -> {
            handler.handle(request);
        });

        assertEquals("Exception while profiling", exception.getMessage());
        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("Execution error", exception.getCause().getMessage());
    }


}
