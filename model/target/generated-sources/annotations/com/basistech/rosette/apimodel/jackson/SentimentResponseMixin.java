package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("SentimentResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.SentimentResponse.SentimentResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SentimentResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class SentimentResponseMixinBuilder {
  }
}
