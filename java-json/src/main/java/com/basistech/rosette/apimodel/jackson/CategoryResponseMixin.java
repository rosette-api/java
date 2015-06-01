package com.basistech.rosette.apimodel.jackson;

import java.util.List;

import com.basistech.rosette.apimodel.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryResponseMixin {
    @JsonCreator
    public CategoryResponseMixin(
            @JsonProperty("requestId") String requestId,
            @JsonProperty("categories") List<Category> categories
    ) {
        //
    }

}
