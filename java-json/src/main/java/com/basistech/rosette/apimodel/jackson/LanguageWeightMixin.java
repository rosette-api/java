package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageWeightMixin {
    @JsonCreator
    protected LanguageWeightMixin(
            @JsonProperty("language") String language,
            @JsonProperty("script") String script,
            @JsonProperty("weight") Integer weight
    ) {
        //
    }

}
