package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("TransliterationResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.TransliterationResponse.TransliterationResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class TransliterationResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class TransliterationResponseMixinBuilder {
  }
}
