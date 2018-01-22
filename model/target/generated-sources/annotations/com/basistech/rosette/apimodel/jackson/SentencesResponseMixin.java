package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("SentencesResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.SentencesResponse.SentencesResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SentencesResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class SentencesResponseMixinBuilder {
  }
}
