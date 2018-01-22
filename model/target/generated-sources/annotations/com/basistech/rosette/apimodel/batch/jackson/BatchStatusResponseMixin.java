package com.basistech.rosette.apimodel.batch.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("BatchStatusResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.batch.BatchStatusResponse.BatchStatusResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BatchStatusResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class BatchStatusResponseMixinBuilder {
  }
}
