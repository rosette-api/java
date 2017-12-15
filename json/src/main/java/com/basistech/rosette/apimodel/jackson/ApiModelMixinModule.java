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
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.SentimentModelType;
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

        MixinUtil.addMixins(context);

        context.setMixInAnnotations(DocumentRequest.class, DocumentRequestMixin.class);
        context.setMixInAnnotations(DocumentRequest.DocumentRequestBuilder.class, DocumentRequestMixin.DocumentRequestBuilderMixin.class);
        context.setMixInAnnotations(AdmRequest.class, AdmRequestMixin.class);
        context.setMixInAnnotations(AdmRequest.AdmRequestBuilder.class, AdmRequestMixin.AdmRequestBuilderMixin.class);

        context.setMixInAnnotations(NameSimilarityRequest.class, NameSimilarityRequestMixin.class);
        context.setMixInAnnotations(NameSimilarityRequest.NameSimilarityRequestBuilder.class, NameSimilarityRequestMixin.NameSimilarityRequestBuilderMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.NameTranslationRequestBuilder.class, NameTranslationRequestMixin.NameTranslationRequestBuilderMixin.class);
        context.setMixInAnnotations(NameDeduplicationRequest.class, NameDeduplicationRequestMixin.class);
        context.setMixInAnnotations(NameDeduplicationRequest.NameDeduplicationRequestBuilder.class,NameDeduplicationRequestMixin.NameDeduplicationRequestBuilderMixin.class);

        // TODO: see if there's something similar that can be used to generalize enum handling
        context.setMixInAnnotations(AccuracyMode.class, AccuracyModeMixin.class);
        SimpleSerializers keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new AccuracyModeSerializer());
        context.addKeySerializers(keySerializers);

        context.setMixInAnnotations(SentimentModelType.class, SentimentModelTypeMixin.class);
        keySerializers = new SimpleSerializers();
        keySerializers.addSerializer(new SentimentModelTypeSerializer());
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
