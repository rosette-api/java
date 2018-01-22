package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("NameTranslationResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.NameTranslationResponse.NameTranslationResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class NameTranslationResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class NameTranslationResponseMixinBuilder {
  }
}
