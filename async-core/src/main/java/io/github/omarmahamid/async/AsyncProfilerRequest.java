package io.github.omarmahamid.async;

public abstract class AsyncProfilerRequest {

    private final String event;
    private final String action;
    private final String outputFile;

    protected AsyncProfilerRequest(String event, String action, String outputFile) {
        this.event = event;
        this.action = action;
        this.outputFile = outputFile;
    }


    public String getEvent() {
        return event;
    }

    public String getAction() {
        return action;
    }

    public String getOutputFile() {
        return outputFile;
    }

}
