package com.basistech.rosette.apimodel.jackson;

import java.util.EnumSet;

import com.basistech.rosette.apimodel.CategorizationModel;
import com.basistech.rosette.apimodel.ProcessorType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityOptionsMixin {
    @JsonCreator
    private EntityOptionsMixin(
            @JsonProperty("resolveNamedEntities") Boolean resolveNamedEntities,
            @JsonProperty("maxEntityTokens") Integer maxEntityTokens,
            @JsonProperty("processors") EnumSet<ProcessorType> processors,
            @JsonProperty("allowPartialGazetteerMatches") Boolean allowPartialGazetteerMatches,
            @JsonProperty("redactorPreferLength") Boolean redactorPreferLength
    ) {
        //
    }

}
