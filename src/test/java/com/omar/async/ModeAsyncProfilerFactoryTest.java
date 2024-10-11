package com.omar.async;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModeAsyncProfilerFactoryTest {


    @Test
    void testGetAsyncProfilerForCPU() {
        IAsyncProfilerHandler handler = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.CPU);
        assertNotNull(handler);
        assertTrue(handler instanceof CPUAsyncProfilerHandler);
    }

    @Test
    void testGetAsyncProfilerForAlloc() {
        IAsyncProfilerHandler handler = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.ALLOC);
        assertNotNull(handler);
        assertTrue(handler instanceof AllocAsyncProfilerHandler);
    }


    @Test
    void testGetAsyncProfilerCachedInstances() {
        IAsyncProfilerHandler handler1 = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.CPU);
        IAsyncProfilerHandler handler2 = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.CPU);

        assertSame(handler1, handler2);
    }

    @Test
    void testGetAsyncProfilerForMultipleModes() {
        IAsyncProfilerHandler cpuHandler = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.CPU);
        IAsyncProfilerHandler allocHandler = ModeAsyncProfilerFactory.getAsyncProfiler(ModeAsyncProfiler.ALLOC);

        assertNotSame(cpuHandler, allocHandler);
    }

}
