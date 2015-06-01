package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameMixin {
    @JsonCreator
    protected NameMixin(
            @JsonProperty("text") String name,
            @JsonProperty("entityType") String entityType,
            @JsonProperty("script") String script,
            @JsonProperty("language") String language
    ) {
        //
    }

}
