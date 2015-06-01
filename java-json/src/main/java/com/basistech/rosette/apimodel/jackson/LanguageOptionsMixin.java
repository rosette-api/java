package com.basistech.rosette.apimodel.jackson;

import java.util.Set;

import com.basistech.rosette.apimodel.LanguageWeight;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LanguageOptionsMixin {
    @JsonCreator
    protected LanguageOptionsMixin(
            @JsonProperty("minValidChars") Integer minValidChars,
            @JsonProperty("profileDepth") Integer profileDepth,
            @JsonProperty("ambiguityThreshold") Double ambiguityThreshold,
            @JsonProperty("invalidityThreshold") Double invalidityThreshold,
            @JsonProperty("languageHint") String languageHint,
            @JsonProperty("languageHintWeight") Double languageHintWeight,
            @JsonProperty("encodingHint") String encodingHint,
            @JsonProperty("encodingHintWeight") Double encodingHintWeight,
            @JsonProperty("languageWeightAdjustments") Set<LanguageWeight> languageWeightAdjustments
    ) {
        //
    }

}
