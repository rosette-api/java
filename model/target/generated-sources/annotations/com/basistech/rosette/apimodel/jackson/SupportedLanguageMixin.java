package com.basistech.rosette.apimodel.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonTypeName("SupportedLanguage")
@JsonDeserialize(
    builder = com.basistech.rosette.apimodel.SupportedLanguage.SupportedLanguageBuilder.class
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class SupportedLanguageMixin {
  @JsonPOJOBuilder(
      withPrefix = ""
  )
  public abstract class SupportedLanguageMixinBuilder {
  }
}
