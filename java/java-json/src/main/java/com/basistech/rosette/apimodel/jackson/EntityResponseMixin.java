package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.ExtractedEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityResponseMixin {
    @JsonCreator
    public EntityResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("entities") List<ExtractedEntity> entities
    ) {
        //
    }

}
