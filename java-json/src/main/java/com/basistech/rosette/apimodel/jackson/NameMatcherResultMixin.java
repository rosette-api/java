package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.Name;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameMatcherResultMixin {
    @JsonCreator
    protected NameMatcherResultMixin(
            @JsonProperty("score") double score
    ) {
        //
    }

}
