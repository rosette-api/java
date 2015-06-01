package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.TranslatedNameResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameTranslationResponseMixin {
    @JsonCreator
    protected NameTranslationResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("result") TranslatedNameResult result
    ) {
        //
    }

}
