package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtractedEntityMixin {
    @JsonCreator
    public ExtractedEntityMixin(
            @JsonProperty("indocChainId") int indocChainId,
            @JsonProperty("type") String type,
            @JsonProperty("mention") String mention,
            @JsonProperty("normalized") String normalized,
            @JsonProperty("count") int count,
            @JsonProperty("confidence") double confidence
    ) {
        //
    }

    @JsonCreator
    public ExtractedEntityMixin(
    ) {
        //
    }
}
