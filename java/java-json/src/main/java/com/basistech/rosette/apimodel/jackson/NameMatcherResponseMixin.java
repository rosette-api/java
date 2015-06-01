package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.NameMatcherResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameMatcherResponseMixin {
    @JsonCreator
    protected NameMatcherResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("result") NameMatcherResult result
    ) {
        //
    }

}
