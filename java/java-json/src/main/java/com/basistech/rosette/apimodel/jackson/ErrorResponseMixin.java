package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseMixin {
    @JsonCreator
    public ErrorResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("code") String code,
            @JsonProperty("message") String message
    ) {
        //
    }

}
