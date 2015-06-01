package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.InputUnit;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class LanguageRequestMixin {
    @JsonCreator
    public LanguageRequestMixin(
            @JsonProperty("content") String content,
            @JsonProperty("contentUri") String contentUri,
            @JsonProperty("contentType") String contentType,
            @JsonProperty("unit") InputUnit unit,
            @JsonProperty("options") LanguageOptions options
    ) {
        //
    }

}
