/*
* Copyright 2014 Basis Technology Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.AccuracyMode;
import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CategoriesRequest;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.EntitySentiment;
import com.basistech.rosette.apimodel.Label;
import com.basistech.rosette.apimodel.ConstantsResponse;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesRequest;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.Entity;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageRequest;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LanguageWeight;
import com.basistech.rosette.apimodel.LinkedEntitiesRequest;
import com.basistech.rosette.apimodel.LinkedEntitiesResponse;
import com.basistech.rosette.apimodel.LinkedEntity;
import com.basistech.rosette.apimodel.MorphologyRequest;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Relationship;
import com.basistech.rosette.apimodel.RelationshipsOptions;
import com.basistech.rosette.apimodel.RelationshipsRequest;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SentencesRequest;
import com.basistech.rosette.apimodel.SentencesResponse;
import com.basistech.rosette.apimodel.SentimentRequest;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.util.jackson.EnumModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

/**
 * Jackson module to configure Json serialization and deserialization for the
 * Rosette API model.
 */
public class ApiModelMixinModule extends EnumModule {

    public ApiModelMixinModule() {
        super();
    }

    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(Response.class, ResponseMixin.class);
        context.setMixInAnnotations(Label.class, LabelMixin.class);
        context.setMixInAnnotations(CategoriesOptions.class, CategoriesOptionsMixin.class);
        context.setMixInAnnotations(CategoriesRequest.class, CategoriesRequestMixin.class);
        context.setMixInAnnotations(CategoriesResponse.class, CategoriesResponseMixin.class);
        context.setMixInAnnotations(ConstantsResponse.class, ConstantsResponseMixin.class);
        context.setMixInAnnotations(EntitiesOptions.class, EntityOptionsMixin.class);
        context.setMixInAnnotations(EntitiesRequest.class, EntityRequestMixin.class);
        context.setMixInAnnotations(EntitiesResponse.class, EntityResponseMixin.class);
        context.setMixInAnnotations(EntitySentiment.class, EntitySentimentMixin.class);
        context.setMixInAnnotations(ErrorResponse.class, ErrorResponseMixin.class);
        context.setMixInAnnotations(Entity.class, ExtractedEntityMixin.class);
        context.setMixInAnnotations(InfoResponse.class, InfoResponseMixin.class);
        context.setMixInAnnotations(LanguageDetectionResult.class, LanguageDetectionResultMixin.class);
        context.setMixInAnnotations(LanguageOptions.class, LanguageOptionsMixin.class);
        context.setMixInAnnotations(LanguageRequest.class, LanguageRequestMixin.class);
        context.setMixInAnnotations(LanguageResponse.class, LanguageResponseMixin.class);
        context.setMixInAnnotations(LanguageWeight.class, LanguageWeightMixin.class);
        context.setMixInAnnotations(LinkedEntity.class, LinkedEntityMixin.class);
        context.setMixInAnnotations(LinkedEntitiesRequest.class, LinkedEntityRequestMixin.class);
        context.setMixInAnnotations(LinkedEntitiesResponse.class, LinkedEntityResponseMixin.class);
        context.setMixInAnnotations(MorphologyRequest.class, MorphologyRequestMixin.class);
        context.setMixInAnnotations(MorphologyResponse.class, MorphologyResponseMixin.class);
        context.setMixInAnnotations(Name.class, NameMixin.class);
        context.setMixInAnnotations(NameSimilarityRequest.class, NameMatcherRequestMixin.class);
        context.setMixInAnnotations(NameSimilarityResponse.class, NameMatcherResponseMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
        context.setMixInAnnotations(NameTranslationResponse.class, NameTranslationResponseMixin.class);
        context.setMixInAnnotations(PingResponse.class, PingResponseMixin.class);
        context.setMixInAnnotations(Request.class, RequestMixin.class);
        context.setMixInAnnotations(SentencesRequest.class, SentencesRequestMixin.class);
        context.setMixInAnnotations(SentencesResponse.class, SentencesResponseMixin.class);
        context.setMixInAnnotations(SentimentRequest.class, SentimentRequestMixin.class);
        context.setMixInAnnotations(SentimentResponse.class, SentimentResponseMixin.class);
        context.setMixInAnnotations(TokensResponse.class, TokenResponseMixin.class);
        context.setMixInAnnotations(RelationshipsRequest.class, RelationshipsRequestMixin.class);
        context.setMixInAnnotations(RelationshipsResponse.class, RelationshipsResponseMixin.class);
        context.setMixInAnnotations(Relationship.class, RelationshipsMixin.class);
        context.setMixInAnnotations(RelationshipsOptions.class, RelationshipsOptionsMixin.class);

        context.setMixInAnnotations(AccuracyMode.class, AccuracyModeMixin.class);
        SimpleSerializers keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new AccuracyModeSerializer());
        context.addKeySerializers(keySerializers);
    }

    /**
     * Register the Rosette API Model Jackson module on an {@link ObjectMapper}.
     *
     * @param mapper the mapper.
     * @return the same mapper, for convenience.
     */
    public static ObjectMapper setupObjectMapper(ObjectMapper mapper) {
        final ApiModelMixinModule module = new ApiModelMixinModule();
        mapper.registerModule(module);
        return mapper;
    }
}
