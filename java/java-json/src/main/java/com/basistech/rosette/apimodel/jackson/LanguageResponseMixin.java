package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageResponseMixin {
    @JsonCreator
    public LanguageResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("languageDetections") List<LanguageDetectionResult> languageDetections
    ) {
        //
    }

}
