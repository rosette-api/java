package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LinguisticsOptionsMixin {
    @JsonCreator
    protected LinguisticsOptionsMixin(
            @JsonProperty("disambiguate") Boolean disambiguate,
            @JsonProperty("query") Boolean query,
            @JsonProperty("tokenizeForScript") Boolean tokenizeForScript,
            @JsonProperty("minNonPrimaryScriptRegionLength") Integer minNonPrimaryScriptRegionLength,
            @JsonProperty("includeHebrewRoots") Boolean includeHebrewRoots,
            @JsonProperty("nfkcNormalize") Boolean nfkcNormalize,
            @JsonProperty("fstTokenize") Boolean fstTokenize,
            @JsonProperty("defaultTokenizationLanguage") String defaultTokenizationLanguageCode
    ) {
        //
    }

}
