package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("CategoriesResponse")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.CategoriesResponse.CategoriesResponseBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class CategoriesResponseMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class CategoriesResponseMixinBuilder {
  }
}
