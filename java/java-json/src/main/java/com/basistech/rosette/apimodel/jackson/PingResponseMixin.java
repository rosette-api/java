package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PingResponseMixin {
    @JsonCreator
    public PingResponseMixin(
            @JsonProperty("message") String message,
            @JsonProperty("time") long time
    ) {
        //
    }

}
