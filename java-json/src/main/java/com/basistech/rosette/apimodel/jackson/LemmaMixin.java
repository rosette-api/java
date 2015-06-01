package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LemmaMixin {
    @JsonCreator
    protected LemmaMixin(
            @JsonProperty("text") String text,
            @JsonProperty("lemma") String lemma
    ) {
        //
    }

}
