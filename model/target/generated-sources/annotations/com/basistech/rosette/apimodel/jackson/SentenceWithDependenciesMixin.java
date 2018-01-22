package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("SentenceWithDependencies")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.SentenceWithDependencies.SentenceWithDependenciesBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SentenceWithDependenciesMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class SentenceWithDependenciesMixinBuilder {
  }
}
