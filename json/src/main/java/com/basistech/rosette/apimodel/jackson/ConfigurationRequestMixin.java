package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.Configuration;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("ConfigurationRequest")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationRequestMixin {
    @JsonCreator
    protected ConfigurationRequestMixin(
        @JsonProperty("profileId") String profileId,
        @JsonProperty("language") LanguageCode language,
        @JsonProperty("configuration") Configuration configuration
    ) {
        //
    }

    @JsonPOJOBuilder(withPrefix = "")
    abstract class ConfigurationRequestBuilderMixin {
    }
}
