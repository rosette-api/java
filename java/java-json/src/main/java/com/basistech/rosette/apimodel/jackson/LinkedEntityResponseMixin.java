package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.LinkedEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LinkedEntityResponseMixin {
    @JsonCreator
    public LinkedEntityResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("entities") List<LinkedEntity> entities
    ) {
        //
    }

}
