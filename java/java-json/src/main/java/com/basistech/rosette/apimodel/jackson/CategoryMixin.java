package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryMixin {
    @JsonCreator
    public CategoryMixin(
            @JsonProperty("label") String label,
            @JsonProperty("confidence") double confidence,
            @JsonProperty("explanations") List<String> explanations
    ) {
        //
    }

}
