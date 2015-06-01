package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HanReadingsMixin {
    @JsonCreator
    public HanReadingsMixin(
            @JsonProperty("text") String text,
            @JsonProperty("hanReadings") List<String> hanReadings
    ) {
        //
    }

}
