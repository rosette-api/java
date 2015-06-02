package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScriptResponseMixin {
    @JsonCreator
    public ScriptResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("scripts") List<String> scripts
    ) {
        //
    }

}
