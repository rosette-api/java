package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SchemeResponseMixin {
    @JsonCreator
    public SchemeResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("scripts") List<String> schemes
    ) {
        //
    }

}
