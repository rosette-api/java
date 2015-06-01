package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameTranslationRequestMixin {
    @JsonCreator
    protected NameTranslationRequestMixin(
            @JsonProperty("name") String name,
            @JsonProperty("entityType") String entityType,
            @JsonProperty("sourceScript") String sourceScript,
            @JsonProperty("sourceLanguageOfOrigin") String sourceLanguageOfOrigin,
            @JsonProperty("sourceLanguageOfUse") String sourceLanguageOfUse,
            @JsonProperty("targetLanguage") String targetLanguage,
            @JsonProperty("targetScript") String targetScript,
            @JsonProperty("targetScheme") String targetScheme
    ) {
        //
    }

}
