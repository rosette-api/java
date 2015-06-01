package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.Name;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NameMatcherRequestMixin {
    @JsonCreator
    protected NameMatcherRequestMixin(
            @JsonProperty("name1") Name name1,
            @JsonProperty("name2") Name name2
    ) {
        //
    }

}
