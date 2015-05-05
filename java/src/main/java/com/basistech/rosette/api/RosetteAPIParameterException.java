package com.basistech.rosette.api;

import com.basistech.rosette.model.ErrorResponse;

public class RosetteAPIParameterException extends Exception {

    private final String code;
    private final String message;

    public RosetteAPIParameterException(ErrorResponse response) {
        super();
        code = response.getCode();
        message = response.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String toString() {
        return this.getClass().getSimpleName() + ", code: " + code + ", message: " + message;
    }
}
