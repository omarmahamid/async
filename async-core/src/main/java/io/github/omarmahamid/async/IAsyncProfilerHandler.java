package io.github.omarmahamid.async;


import io.github.omarmahamid.async.exception.AsyncProfilerException;

public interface IAsyncProfilerHandler {

    void handle(AsyncProfilerRequest request) throws AsyncProfilerException;

}
