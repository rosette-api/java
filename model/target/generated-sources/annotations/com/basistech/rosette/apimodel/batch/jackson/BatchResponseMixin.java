package com.basistech.rosette.apimodel.batch.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("BatchResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.batch.BatchResponse.BatchResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BatchResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class BatchResponseMixinBuilder {
  }
}
