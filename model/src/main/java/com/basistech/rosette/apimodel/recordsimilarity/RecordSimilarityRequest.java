/*
 * Copyright 2022 Basis Technology Corp.
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

package com.basistech.rosette.apimodel.recordsimilarity;

import com.basistech.rosette.apimodel.Request;
import com.basistech.rosette.apimodel.recordsimilarity.deserializers.RecordSimilarityDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@JsonDeserialize(builder = RecordSimilarityRequest.RecordSimilarityRequestBuilderImpl.class, using = RecordSimilarityDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode
@Getter
public class RecordSimilarityRequest extends Request {
    private final Map<String, RecordSimilarityFieldInfo> fields;
    private final RecordSimilarityProperties properties;
    private final RecordSimilarityRecords records;

    protected RecordSimilarityRequest(RecordSimilarityRequestBuilder<?, ?> b) {
        super(b);
        this.fields = b.fields;
        this.properties = b.properties;
        this.records = b.records;
    }

    public static RecordSimilarityRequestBuilder<?, ?> builder() {
        return new RecordSimilarityRequestBuilderImpl();
    }

    public abstract static class RecordSimilarityRequestBuilder<C extends RecordSimilarityRequest, B extends RecordSimilarityRequestBuilder<C, B>> extends RequestBuilder<C, B> {
        private Map<String, RecordSimilarityFieldInfo> fields;
        private RecordSimilarityProperties properties;
        private RecordSimilarityRecords records;

        public B fields(Map<String, RecordSimilarityFieldInfo> fields) {
            this.fields = fields;
            return self();
        }

        public B properties(RecordSimilarityProperties properties) {
            this.properties = properties;
            return self();
        }

        public B records(RecordSimilarityRecords records) {
            this.records = records;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        @Override
        public String toString() {
            return "RecordSimilarityRequest.RecordSimilarityRequestBuilder(super=" + super.toString() + ", fields=" + this.fields + ", properties=" + this.properties + ", records=" + this.records + ")";
        }
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    static final class RecordSimilarityRequestBuilderImpl extends RecordSimilarityRequestBuilder<RecordSimilarityRequest, RecordSimilarityRequestBuilderImpl> {
        private RecordSimilarityRequestBuilderImpl() {
        }

        protected RecordSimilarityRequestBuilderImpl self() {
            return this;
        }

        public RecordSimilarityRequest build() {
            return new RecordSimilarityRequest(this);
        }
    }
}
