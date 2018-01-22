package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("RelationshipsResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.RelationshipsResponse.RelationshipsResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class RelationshipsResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class RelationshipsResponseMixinBuilder {
  }
}
