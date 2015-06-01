package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SentenceResponseMixin {
    @JsonCreator
    public SentenceResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("sentences") List<String> sentences
    ) {
        //
    }

}
