package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SentimentOptionsMixin {
    @JsonCreator
    protected SentimentOptionsMixin(
            @JsonProperty("model") String model,
            @JsonProperty("explain") Boolean explain
    ) {
        //
    }

}
