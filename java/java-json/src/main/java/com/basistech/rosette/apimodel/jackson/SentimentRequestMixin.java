package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SentimentRequestMixin {
    @JsonCreator
    protected SentimentRequestMixin(
            @JsonProperty("language") String language,
            @JsonProperty("content") String content,
            @JsonProperty("contentUri") String contentUri,
            @JsonProperty("contentType") String contentType,
            @JsonProperty("unit") InputUnit unit,
            @JsonProperty("options") SentimentOptions options
    ) {
        //
    }

}
