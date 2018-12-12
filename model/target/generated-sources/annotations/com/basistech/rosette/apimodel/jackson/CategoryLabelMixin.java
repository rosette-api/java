package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("CategoryLabel")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.CategoryLabel.CategoryLabelBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class CategoryLabelMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class CategoryLabelMixinBuilder {
  }
}
