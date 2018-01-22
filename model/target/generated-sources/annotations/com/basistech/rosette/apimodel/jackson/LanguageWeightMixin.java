package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("LanguageWeight")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.LanguageWeight.LanguageWeightBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class LanguageWeightMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class LanguageWeightMixinBuilder {
  }
}
