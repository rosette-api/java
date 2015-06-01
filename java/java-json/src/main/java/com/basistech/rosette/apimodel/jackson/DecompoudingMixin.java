package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DecompoudingMixin {
    @JsonCreator
    public DecompoudingMixin(
            @JsonProperty("text") String text,
            @JsonProperty("compoundComponents") List<String> compoundComponents
    ) {
        //
    }

}
