package com.basistech.rosette.api;

import com.basistech.rosette.model.ErrorResponse;

public class RosetteAPIException extends Exception {

    private final int httpStatusCode;
    private final String requestId;
    private final String code;
    private final String message;

    public RosetteAPIException(int httpStatusCode, ErrorResponse response) {
        super();
        this.httpStatusCode = httpStatusCode;
        code = response.getCode();
        message = response.getMessage();
        requestId = response.getRequestId();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getRequestId() {
        return requestId;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " http status code: " + httpStatusCode + ", requestId: " + requestId + ", code: " + code + ", message: " + message;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
