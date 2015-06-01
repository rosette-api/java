package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMixin {
    @JsonCreator
    public ResponseMixin(@JsonProperty("requestId") String requestId) {
        //
    }

}
