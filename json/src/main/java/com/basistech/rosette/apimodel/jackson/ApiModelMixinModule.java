/*
* Copyright 2018 Basis Technology Corp.
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

import com.basistech.rosette.apimodel.AddressSimilarityRequest;
import com.basistech.rosette.apimodel.AdmRequest;
import com.basistech.rosette.apimodel.ConfigurationRequest;
import com.basistech.rosette.apimodel.DocumentRequest;
import com.basistech.rosette.apimodel.FieldedAddress;
import com.basistech.rosette.apimodel.IAddress;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.NameDeduplicationRequest;
import com.basistech.rosette.apimodel.NameSimilarityRequest;
import com.basistech.rosette.apimodel.NameTranslationRequest;
import com.basistech.rosette.apimodel.UnfieldedAddress;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityDeserializer;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.dm.jackson.AnnotatedDataModelModule;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;

/**
 * Jackson module to configure Json serialization and deserialization for the
 * Rosette API model.
 */
public class ApiModelMixinModule extends AnnotatedDataModelModule {

    public ApiModelMixinModule() {
        super();
    }

    @Override
    public void setupModule(Module.SetupContext context) {

        super.setupModule(context);

        MixinUtil.addMixins(context);

        context.setMixInAnnotations(DocumentRequest.class, DocumentRequestMixin.class);
        context.setMixInAnnotations(DocumentRequest.DocumentRequestBuilder.class,
                DocumentRequestMixin.DocumentRequestBuilderMixin.class);
        context.setMixInAnnotations(AdmRequest.class, AdmRequestMixin.class);
        context.setMixInAnnotations(AdmRequest.AdmRequestBuilder.class, AdmRequestMixin.AdmRequestBuilderMixin.class);

        context.setMixInAnnotations(Name.class, NameMixin.class);
        context.setMixInAnnotations(Name.NameBuilder.class, NameMixin.NameBuilderMixin.class);
        context.setMixInAnnotations(NameSimilarityRequest.class, NameSimilarityRequestMixin.class);
        context.setMixInAnnotations(NameSimilarityRequest.NameSimilarityRequestBuilder.class,
                NameSimilarityRequestMixin.NameSimilarityRequestBuilderMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.class, NameTranslationRequestMixin.class);
        context.setMixInAnnotations(NameTranslationRequest.NameTranslationRequestBuilder.class,
                NameTranslationRequestMixin.NameTranslationRequestBuilderMixin.class);
        context.setMixInAnnotations(NameDeduplicationRequest.class, NameDeduplicationRequestMixin.class);
        context.setMixInAnnotations(NameDeduplicationRequest.NameDeduplicationRequestBuilder.class,
                NameDeduplicationRequestMixin.NameDeduplicationRequestBuilderMixin.class);

        context.setMixInAnnotations(FieldedAddress.class, FieldedAddressMixin.class);
        context.setMixInAnnotations(FieldedAddress.FieldedAddressBuilder.class,
                FieldedAddressMixin.FieldedAddressBuilderMixin.class);
        context.setMixInAnnotations(UnfieldedAddress.class, UnfieldedAddressMixin.class);
        context.setMixInAnnotations(UnfieldedAddress.UnfieldedAddressBuilder.class,
                UnfieldedAddressMixin.UnfieldedAddressBuilderMixin.class);
        context.setMixInAnnotations(AddressSimilarityRequest.class, AddressSimilarityRequestMixin.class);
        context.setMixInAnnotations(AddressSimilarityRequest.AddressSimilarityRequestBuilder.class,
                AddressSimilarityRequestMixin.AddressSimilarityRequestBuilderMixin.class);
        context.setMixInAnnotations(ConfigurationRequest.class, ConfigurationRequestMixin.class);
        context.setMixInAnnotations(ConfigurationRequest.ConfigurationRequestBuilder.class,
            ConfigurationRequestMixin.ConfigurationRequestBuilderMixin.class);

        // IAddresses require a custom deserializer
        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(IAddress.class, new AddressDeserializer());
        deserializers.addDeserializer(RecordSimilarityRequest.class, new RecordSimilarityDeserializer());
        context.addDeserializers(deserializers);
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
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper;
    }
}
