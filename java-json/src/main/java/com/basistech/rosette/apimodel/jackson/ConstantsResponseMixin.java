package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConstantsResponseMixin {
    @JsonCreator
    public ConstantsResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("version") String version,
            @JsonProperty("build") String build,
            @JsonProperty("support") Object support
    ) {
        //
    }

}
