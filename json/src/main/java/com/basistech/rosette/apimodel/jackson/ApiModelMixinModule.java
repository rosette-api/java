/*
* Copyright 2017 Basis Technology Corp.
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
import com.basistech.rosette.apimodel.AdmRequest;
import com.basistech.rosette.apimodel.CategoriesOptions;
import com.basistech.rosette.apimodel.CategoriesResponse;
import com.basistech.rosette.apimodel.ConstantsResponse;
import com.basistech.rosette.apimodel.Dependency;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.EntitiesOptions;
import com.basistech.rosette.apimodel.EntitiesResponse;
import com.basistech.rosette.apimodel.EntityMention;
import com.basistech.rosette.apimodel.EntitySentiment;
import com.basistech.rosette.apimodel.ErrorResponse;
import com.basistech.rosette.apimodel.InfoResponse;
import com.basistech.rosette.apimodel.Label;
import com.basistech.rosette.apimodel.LanguageDetectionResult;
import com.basistech.rosette.apimodel.LanguageOptions;
import com.basistech.rosette.apimodel.LanguageResponse;
import com.basistech.rosette.apimodel.LanguageWeight;
import com.basistech.rosette.apimodel.MorphologyOptions;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameDeduplicationResponse;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameSimilarityResponse;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.NameTranslationResponse;
import com.basistech.rosette.apimodel.Options;
import com.basistech.rosette.apimodel.PingResponse;
import com.basistech.rosette.apimodel.Relationship;
import com.basistech.rosette.apimodel.RelationshipsOptions;
import com.basistech.rosette.apimodel.RelationshipsResponse;
import com.basistech.rosette.apimodel.Response;
import com.basistech.rosette.apimodel.SentenceWithDependencies;
import com.basistech.rosette.apimodel.SentencesResponse;
import com.basistech.rosette.apimodel.SentimentOptions;
import com.basistech.rosette.apimodel.SentimentResponse;
import com.basistech.rosette.apimodel.SyntaxDependenciesResponse;
import com.basistech.rosette.apimodel.TextEmbeddingResponse;
import com.basistech.rosette.apimodel.TokensResponse;
import com.basistech.rosette.apimodel.TransliterationOptions;
import com.basistech.rosette.apimodel.TransliterationResponse;
import com.basistech.rosette.apimodel.batch.BatchRequest;
import com.basistech.rosette.apimodel.batch.BatchRequestItem;
import com.basistech.rosette.apimodel.batch.BatchResponse;
import com.basistech.rosette.apimodel.batch.BatchStatusResponse;
import com.basistech.rosette.apimodel.jackson.batch.BatchRequestItemMixin;
import com.basistech.rosette.apimodel.jackson.batch.BatchRequestMixin;
import com.basistech.rosette.apimodel.jackson.batch.BatchResponseMixin;
import com.basistech.rosette.apimodel.jackson.batch.BatchStatusResponseMixin;
import com.basistech.rosette.dm.jackson.AnnotatedDataModelModule;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleSerializers;

/**
 * Jackson module to configure Json serialization and deserialization for the
 * Rosette API model.
 */
@SuppressWarnings("deprecation")
public class ApiModelMixinModule extends AnnotatedDataModelModule {

    public ApiModelMixinModule() {
        super();
    }

    public void setupModule(Module.SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(Response.class, ResponseMixin.class);
        context.setMixInAnnotations(Label.class, LabelMixin.class);
        context.setMixInAnnotations(CategoriesOptions.class, CategoriesOptionsMixin.class);
        context.setMixInAnnotations(CategoriesResponse.class, CategoriesResponseMixin.class);
        context.setMixInAnnotations(ConstantsResponse.class, ConstantsResponseMixin.class);
        context.setMixInAnnotations(EntitiesOptions.class, EntitiesOptionsMixin.class);
        context.setMixInAnnotations(EntitiesResponse.class, EntityResponseMixin.class);
        context.setMixInAnnotations(EntitySentiment.class, EntitySentimentMixin.class);
        context.setMixInAnnotations(ErrorResponse.class, ErrorResponseMixin.class);
        context.setMixInAnnotations(EntityMention.class, EntityMentionMixin.class);
        context.setMixInAnnotations(InfoResponse.class, InfoResponseMixin.class);
        context.setMixInAnnotations(LanguageDetectionResult.class, LanguageDetectionResultMixin.class);
        context.setMixInAnnotations(LanguageOptions.class, LanguageOptionsMixin.class);
        context.setMixInAnnotations(LanguageResponse.class, LanguageResponseMixin.class);
        context.setMixInAnnotations(LanguageWeight.class, LanguageWeightMixin.class);
        context.setMixInAnnotations(com.basistech.rosette.apimodel.LinkedEntity.class, LinkedEntityMixin.class);
        context.setMixInAnnotations(com.basistech.rosette.apimodel.LinkedEntitiesResponse.class, LinkedEntityResponseMixin.class);
        context.setMixInAnnotations(MorphologyOptions.class, MorphologyOptionsMixin.class);
        context.setMixInAnnotations(MorphologyResponse.class, MorphologyResponseMixin.class);
        context.setMixInAnnotations(Name.class, NameMixin.class);
        context.setMixInAnnotations(NameDeduplicationRequest.class, NameDeduplicationRequestMixin.class);
        context.setMixInAnnotations(NameDeduplicationResponse.class, NameDeduplicationResponseMixin.class);
        context.setMixInAnnotations(NameSimilarityRequest.class, NameSimilarityRequestMixin.class);
        context.setMixInAnnotations(NameSimilarityResponse.class, NameSimilarityResponseMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
        context.setMixInAnnotations(NameTranslationResponse.class, NameTranslationResponseMixin.class);
        context.setMixInAnnotations(PingResponse.class, PingResponseMixin.class);
        context.setMixInAnnotations(DocumentRequest.class, DocumentRequestMixin.class);
        context.setMixInAnnotations(AdmRequest.class, AdmRequestMixin.class);
        context.setMixInAnnotations(SentencesResponse.class, SentencesResponseMixin.class);
        context.setMixInAnnotations(SentimentOptions.class, SentimentOptionsMixin.class);
        context.setMixInAnnotations(SentimentResponse.class, SentimentResponseMixin.class);
        context.setMixInAnnotations(TokensResponse.class, TokenResponseMixin.class);
        context.setMixInAnnotations(RelationshipsResponse.class, RelationshipsResponseMixin.class);
        context.setMixInAnnotations(Relationship.class, RelationshipsMixin.class);
        context.setMixInAnnotations(RelationshipsOptions.class, RelationshipsOptionsMixin.class);
        context.setMixInAnnotations(Options.class, OptionsMixin.class);
        context.setMixInAnnotations(TransliterationOptions.class, TransliterationOptionsMixin.class);
        context.setMixInAnnotations(TransliterationResponse.class, TransliterationResponseMixin.class);

        context.setMixInAnnotations(AccuracyMode.class, AccuracyModeMixin.class);
        SimpleSerializers keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new AccuracyModeSerializer());
        context.addKeySerializers(keySerializers);

        context.setMixInAnnotations(BatchRequest.class, BatchRequestMixin.class);
        context.setMixInAnnotations(BatchRequestItem.class, BatchRequestItemMixin.class);
        context.setMixInAnnotations(BatchResponse.class, BatchResponseMixin.class);

        context.setMixInAnnotations(BatchStatusResponse.class, BatchStatusResponseMixin.class);
        context.setMixInAnnotations(TextEmbeddingResponse.class, TextEmbeddingResponseMixin.class);

        context.setMixInAnnotations(SyntaxDependenciesResponse.class, SyntaxDependenciesResponseMixin.class);
        context.setMixInAnnotations(SentenceWithDependencies.class, SentenceWithDependenciesMixin.class);
        context.setMixInAnnotations(Dependency.class, DependencyMixin.class);
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
