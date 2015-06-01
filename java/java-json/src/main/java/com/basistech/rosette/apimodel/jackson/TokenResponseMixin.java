package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponseMixin {
    @JsonCreator
    public TokenResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("tokens") List<String> tokens
    ) {
        //
    }

}
