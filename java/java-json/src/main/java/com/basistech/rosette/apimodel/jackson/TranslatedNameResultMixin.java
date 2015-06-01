package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TranslatedNameResultMixin {
    @JsonCreator
    protected TranslatedNameResultMixin(
            @JsonProperty("sourceScript") String sourceScript,
            @JsonProperty("sourceLanguageOfOrigin") String sourceLanguageOfOrigin,
            @JsonProperty("sourceLanguageOfUse") String sourceLanguageOfUse,
            @JsonProperty("translation") String translation,
            @JsonProperty("targetLanguage") String targetLanguage,
            @JsonProperty("targetScript") String targetScript,
            @JsonProperty("targetScheme") String targetScheme,
            @JsonProperty("confidence") double confidence
    ) {
        //
    }

}
