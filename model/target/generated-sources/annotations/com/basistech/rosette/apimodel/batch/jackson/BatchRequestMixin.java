package com.basistech.rosette.apimodel.batch.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("BatchRequest")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.batch.BatchRequest.BatchRequestBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BatchRequestMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class BatchRequestMixinBuilder {
  }
}
