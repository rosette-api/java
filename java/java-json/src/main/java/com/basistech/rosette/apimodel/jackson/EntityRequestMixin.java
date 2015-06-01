package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.EntityOptions;
import com.basistech.rosette.apimodel.InputUnit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class EntityRequestMixin {
    @JsonCreator
    public EntityRequestMixin(
            @JsonProperty("language") String language,
            @JsonProperty("content") String content,
            @JsonProperty("contentUri") String contentUri,
            @JsonProperty("contentType") String contentType,
            @JsonProperty("unit") InputUnit unit,
            @JsonProperty("options") EntityOptions options
    ) {
        //
    }

}
