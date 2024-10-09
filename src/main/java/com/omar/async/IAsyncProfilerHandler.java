package com.omar.async;

import com.omar.async.exception.AsyncProfilerException;

public interface IAsyncProfilerHandler {

    void handle(AsyncProfilerRequest request) throws AsyncProfilerException;

}
