package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryOptionsMixin {
    @JsonCreator
    private CategoryOptionsMixin(
            @JsonProperty("model") String model,
            @JsonProperty("explain") Boolean explain,
            @JsonProperty("numCategories") Integer numCategories
    ) {
        //
    }

}
