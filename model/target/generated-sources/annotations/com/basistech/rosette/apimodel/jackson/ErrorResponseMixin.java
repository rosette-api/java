package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("ErrorResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.ErrorResponse.ErrorResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ErrorResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class ErrorResponseMixinBuilder {
  }
}
