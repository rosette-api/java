package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkedEntityMixin {
    @JsonCreator
    protected LinkedEntityMixin(
            @JsonProperty("entityId") String entityId,
            @JsonProperty("indocChainId") int indocChainId,
            @JsonProperty("mention") String mention,
            @JsonProperty("confidence") double confidence
    ) {
        //
    }

}
