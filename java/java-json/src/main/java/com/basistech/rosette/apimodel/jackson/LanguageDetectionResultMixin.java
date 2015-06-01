package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageDetectionResultMixin {
    @JsonCreator
    public LanguageDetectionResultMixin(
            @JsonProperty("language") String language,
            @JsonProperty("confidence") double confidence
    ) {
        //
    }

}
