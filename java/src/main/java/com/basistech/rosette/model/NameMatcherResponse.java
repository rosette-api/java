package com.basistech.rosette.model;

public final class NameMatcherResponse extends Response {
    private NameMatcherResult result;

    public NameMatcherResponse() {}

    public NameMatcherResponse(String requestId,
                               NameMatcherResult result
    ) {
        super(requestId);
        this.result = result;
    }

    public NameMatcherResult getResult() {
        return result;
    }
}