package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PartOfSpeechMixin {
    @JsonCreator
    public PartOfSpeechMixin(
            @JsonProperty("text") String text,
            @JsonProperty("pos") String pos
    ) {
        //
    }

}
