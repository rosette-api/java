package com.basistech.rosette.apimodel.jackson;

import java.util.Map;
import java.util.Set;

import com.basistech.rosette.apimodel.ISO15924;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageInfoResponseMixin {
    @JsonCreator
    public LanguageInfoResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("supportedLanguages") Map<String, Set<ISO15924>> supportedLanguages,
            @JsonProperty("supportedScripts") Map<ISO15924, Set<String>> supportedScripts
    ) {
        //
    }

}
