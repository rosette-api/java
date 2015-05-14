package com.basistech.rosette.model;

public class PingResponse extends Response {
    private String message;
    private long time;

    public PingResponse() {
    }

    public PingResponse(String message, long time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }
}
