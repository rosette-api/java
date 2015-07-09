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

import com.basistech.rosette.apimodel.CategoriesRequest;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.Category;
import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CompoundComponents;
import com.basistech.rosette.apimodel.ConstantsResponse;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.Entity;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageInfoResponse;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LanguageWeight;
import com.basistech.rosette.apimodel.Lemma;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.LinkedEntity;
import com.basistech.rosette.apimodel.LinkedEntitiesRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameMatchingRequest;
import com.basistech.rosette.apimodel.NameMatchingResponse;
import com.basistech.rosette.apimodel.NameMatchingResult;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.PartOfSpeech;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SchemesResponse;
import com.basistech.rosette.apimodel.ScriptResponse;
import com.basistech.rosette.apimodel.SentencesResponse;
import com.basistech.rosette.apimodel.Sentiment;
import com.basistech.rosette.apimodel.SentimentModel;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.NameTranslationResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

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
            context.setMixInAnnotations(CategoriesOptions.class, CategoryOptionsMixin.class);
            context.setMixInAnnotations(CategoriesRequest.class, CategoryRequestMixin.class);
            context.setMixInAnnotations(CategoriesResponse.class, CategoryResponseMixin.class);
            context.setMixInAnnotations(ConstantsResponse.class, ConstantsResponseMixin.class);
            context.setMixInAnnotations(CompoundComponents.class, DecompoudingMixin.class);
            context.setMixInAnnotations(EntitiesOptions.class, EntityOptionsMixin.class);
            context.setMixInAnnotations(EntitiesRequest.class, EntityRequestMixin.class);
            context.setMixInAnnotations(EntitiesResponse.class, EntityResponseMixin.class);
            context.setMixInAnnotations(ErrorResponse.class, ErrorResponseMixin.class);
            context.setMixInAnnotations(Entity.class, ExtractedEntityMixin.class);
            context.setMixInAnnotations(HanReadings.class, HanReadingsMixin.class);
            context.setMixInAnnotations(InfoResponse.class, InfoResponseMixin.class);
            context.setMixInAnnotations(LanguageDetectionResult.class, LanguageDetectionResultMixin.class);
            context.setMixInAnnotations(LanguageInfoResponse.class, LanguageInfoResponseMixin.class);
            context.setMixInAnnotations(LanguageOptions.class, LanguageOptionsMixin.class);
            context.setMixInAnnotations(LanguageRequest.class, LanguageRequestMixin.class);
            context.setMixInAnnotations(LanguageResponse.class, LanguageResponseMixin.class);
            context.setMixInAnnotations(LanguageWeight.class, LanguageWeightMixin.class);
            context.setMixInAnnotations(Lemma.class, LemmaMixin.class);
            context.setMixInAnnotations(MorphologyOptions.class, LinguisticsOptionsMixin.class);
            context.setMixInAnnotations(MorphologyRequest.class, LinguisticsRequestMixin.class);
            context.setMixInAnnotations(LinkedEntity.class, LinkedEntityMixin.class);
            context.setMixInAnnotations(LinkedEntitiesRequest.class, LinkedEntityRequestMixin.class);
            context.setMixInAnnotations(LinkedEntitiesResponse.class, LinkedEntityResponseMixin.class);
            context.setMixInAnnotations(MorphologyResponse.class, MorphologyResponseMixin.class);
            context.setMixInAnnotations(Name.class, NameMixin.class);
            context.setMixInAnnotations(NameMatchingRequest.class, NameMatcherRequestMixin.class);
            context.setMixInAnnotations(NameMatchingResponse.class, NameMatcherResponseMixin.class);
            context.setMixInAnnotations(NameMatchingResult.class, NameMatcherResultMixin.class);
            context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
            context.setMixInAnnotations(NameTranslationResponse.class, NameTranslationResponseMixin.class);
            context.setMixInAnnotations(PartOfSpeech.class, PartOfSpeechMixin.class);
            context.setMixInAnnotations(PingResponse.class, PingResponseMixin.class);
            context.setMixInAnnotations(Request.class, RequestMixin.class);
            context.setMixInAnnotations(Response.class, ResponseMixin.class);
            context.setMixInAnnotations(SchemesResponse.class, SchemeResponseMixin.class);
            context.setMixInAnnotations(ScriptResponse.class, ScriptResponseMixin.class);
            context.setMixInAnnotations(Sentiment.class, SentimentMixin.class);
            context.setMixInAnnotations(SentimentOptions.class, SentimentOptionsMixin.class);
            context.setMixInAnnotations(SentimentRequest.class, SentimentRequestMixin.class);
            context.setMixInAnnotations(SentimentResponse.class, SentimentResponseMixin.class);
            context.setMixInAnnotations(SentencesResponse.class, SentenceResponseMixin.class);
            context.setMixInAnnotations(TokensResponse.class, TokenResponseMixin.class);
            context.setMixInAnnotations(NameTranslationResult.class, TranslatedNameResultMixin.class);

            context.setMixInAnnotations(SentimentModel.class, SentimentModelMixin.class);
            SimpleSerializers keySerializers = new SimpleSerializers();
            keySerializers.addSerializer(new SentimentModelSerializer());
            context.addKeySerializers(keySerializers);
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
