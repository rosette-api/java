package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoResponseMixin {
    @JsonCreator
    public InfoResponseMixin(
            @JsonProperty("name") String name,
            @JsonProperty("version") String version,
            @JsonProperty("buildNumber") String buildNumber,
            @JsonProperty("buildTime") String buildTime
    ) {
        //
    }

}
