package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.TranslatedNameResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchNameTranslationResponseMixin {
    @JsonCreator
    public BatchNameTranslationResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("results") List<TranslatedNameResult> results
    ) {
        //
    }

}
