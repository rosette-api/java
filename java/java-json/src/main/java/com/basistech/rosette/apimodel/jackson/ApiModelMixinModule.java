/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.Category;
import com.basistech.rosette.apimodel.CategoryOptions;
import com.basistech.rosette.apimodel.CategoryRequest;
import com.basistech.rosette.apimodel.CategoryResponse;
import com.basistech.rosette.apimodel.ConstantsResponse;
import com.basistech.rosette.apimodel.Decompounding;
import com.basistech.rosette.apimodel.EntityOptions;
import com.basistech.rosette.apimodel.EntityRequest;
import com.basistech.rosette.apimodel.EntityResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.ExtractedEntity;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageInfoResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LanguageWeight;
import com.basistech.rosette.apimodel.Lemma;
import com.basistech.rosette.apimodel.LinguisticsOptions;
import com.basistech.rosette.apimodel.LinguisticsRequest;
import com.basistech.rosette.apimodel.LinkedEntity;
import com.basistech.rosette.apimodel.LinkedEntityRequest;
import com.basistech.rosette.apimodel.LinkedEntityResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameMatcherRequest;
import com.basistech.rosette.apimodel.NameMatcherResponse;
import com.basistech.rosette.apimodel.NameMatcherResult;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.PartOfSpeech;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SchemeResponse;
import com.basistech.rosette.apimodel.ScriptResponse;
import com.basistech.rosette.apimodel.SentenceResponse;
import com.basistech.rosette.apimodel.Sentiment;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokenResponse;
import com.basistech.rosette.apimodel.TranslatedNameResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Jackson module to configure Json serialization and deserialization for the
 * Rosette API model.
 */
public class ApiModelMixinModule extends SimpleModule {

        public ApiModelMixinModule() {
            super();
        }

        public void setupModule(Module.SetupContext context) {
            super.setupModule(context);
            context.setMixInAnnotations(Category.class, CategoryMixin.class);
            context.setMixInAnnotations(CategoryOptions.class, CategoryOptionsMixin.class);
            context.setMixInAnnotations(CategoryRequest.class, CategoryRequestMixin.class);
            context.setMixInAnnotations(CategoryResponse.class, CategoryResponseMixin.class);
            context.setMixInAnnotations(ConstantsResponse.class, ConstantsResponseMixin.class);
            context.setMixInAnnotations(Decompounding.class, DecompoudingMixin.class);
            context.setMixInAnnotations(EntityOptions.class, EntityOptionsMixin.class);
            context.setMixInAnnotations(EntityRequest.class, EntityRequestMixin.class);
            context.setMixInAnnotations(EntityResponse.class, EntityResponseMixin.class);
            context.setMixInAnnotations(ErrorResponse.class, ErrorResponseMixin.class);
            context.setMixInAnnotations(ExtractedEntity.class, ExtractedEntityMixin.class);
            context.setMixInAnnotations(HanReadings.class, HanReadingsMixin.class);
            context.setMixInAnnotations(InfoResponse.class, InfoResponseMixin.class);
            context.setMixInAnnotations(LanguageDetectionResult.class, LanguageDetectionResultMixin.class);
            context.setMixInAnnotations(LanguageInfoResponse.class, LanguageInfoResponseMixin.class);
            context.setMixInAnnotations(LanguageOptions.class, LanguageOptionsMixin.class);
            context.setMixInAnnotations(LanguageRequest.class, LanguageRequestMixin.class);
            context.setMixInAnnotations(LanguageResponse.class, LanguageResponseMixin.class);
            context.setMixInAnnotations(LanguageWeight.class, LanguageWeightMixin.class);
            context.setMixInAnnotations(Lemma.class, LemmaMixin.class);
            context.setMixInAnnotations(LinguisticsOptions.class, LinguisticsOptionsMixin.class);
            context.setMixInAnnotations(LinguisticsRequest.class, LinguisticsRequestMixin.class);
            context.setMixInAnnotations(LinkedEntity.class, LinkedEntityMixin.class);
            context.setMixInAnnotations(LinkedEntityRequest.class, LinkedEntityRequestMixin.class);
            context.setMixInAnnotations(LinkedEntityResponse.class, LinkedEntityResponseMixin.class);
            context.setMixInAnnotations(MorphologyResponse.class, MorphologyResponseMixin.class);
            context.setMixInAnnotations(Name.class, NameMixin.class);
            context.setMixInAnnotations(NameMatcherRequest.class, NameMatcherRequestMixin.class);
            context.setMixInAnnotations(NameMatcherResponse.class, NameMatcherResponseMixin.class);
            context.setMixInAnnotations(NameMatcherResult.class, NameMatcherResultMixin.class);
            context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
            context.setMixInAnnotations(NameTranslationResponse.class, NameTranslationResponseMixin.class);
            context.setMixInAnnotations(PartOfSpeech.class, PartOfSpeechMixin.class);
            context.setMixInAnnotations(PingResponse.class, PingResponseMixin.class);
            context.setMixInAnnotations(Request.class, RequestMixin.class);
            context.setMixInAnnotations(Response.class, ResponseMixin.class);
            context.setMixInAnnotations(SchemeResponse.class, SchemeResponseMixin.class);
            context.setMixInAnnotations(ScriptResponse.class, ScriptResponseMixin.class);
            context.setMixInAnnotations(Sentiment.class, SentimentMixin.class);
            context.setMixInAnnotations(SentimentOptions.class, SentimentOptionsMixin.class);
            context.setMixInAnnotations(SentimentRequest.class, SentimentRequestMixin.class);
            context.setMixInAnnotations(SentimentResponse.class, SentimentResponseMixin.class);
            context.setMixInAnnotations(SentenceResponse.class, SentenceResponseMixin.class);
            context.setMixInAnnotations(TokenResponse.class, TokenResponseMixin.class);
            context.setMixInAnnotations(TranslatedNameResult.class, TranslatedNameResultMixin.class);
        }

        /**
         * Register the Rosette API Model Jackson module on an {@link ObjectMapper}.
         * @param mapper the mapper.
         * @return the same mapper, for convenience.
         */
        public static ObjectMapper setupObjectMapper(ObjectMapper mapper) {
            mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
            final ApiModelMixinModule module = new ApiModelMixinModule();
            mapper.registerModule(module);
            return mapper;
        }
}
