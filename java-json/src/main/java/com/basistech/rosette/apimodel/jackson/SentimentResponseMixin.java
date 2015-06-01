package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.Sentiment;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SentimentResponseMixin {
    @JsonCreator
    protected SentimentResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("sentiment") List<Sentiment> sentiment
    ) {
        //
    }

}
