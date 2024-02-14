/*
 * Copyright 2024 Basis Technology Corp.
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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import com.basistech.rosette.apimodel.recordsimilarity.deserializers.RecordSimilarityResultDeserializer;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordSimilarityField;

@JsonDeserialize(builder = RecordSimilarityResult.RecordSimilarityResultBuilderImpl.class, using = RecordSimilarityResultDeserializer.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode
@Getter
public class RecordSimilarityResult {
    Double score;
    Map<String, RecordSimilarityField> left;
    Map<String, RecordSimilarityField> right;
    Map<String, RecordSimilarityExplainInfo> explainInfo;

    protected RecordSimilarityResult(RecordSimilarityResult.RecordSimilarityResultBuilder<?, ?> b) {
        this.score = b.score;
        this.left = b.left;
        this.right = b.right;
        this.explainInfo = b.explainInfo;
    }

    public static RecordSimilarityResult.RecordSimilarityResultBuilder<?, ?> builder() {
        return new RecordSimilarityResult.RecordSimilarityResultBuilderImpl();
    }

    public abstract static class RecordSimilarityResultBuilder<C extends RecordSimilarityResult, B extends RecordSimilarityResult.RecordSimilarityResultBuilder<C, B>> {
        private Double score;
        private Map<String, RecordSimilarityField> left;
        private Map<String, RecordSimilarityField> right;
        private Map<String, RecordSimilarityExplainInfo> explainInfo;

        public B score(Double score) {
            this.score = score;
            return self();
        }

        public B left(Map<String, RecordSimilarityField> left) {
            this.left = left;
            return self();
        }

        public B right(Map<String, RecordSimilarityField> right) {
            this.right = right;
            return self();
        }

        public B explainInfo(Map<String, RecordSimilarityExplainInfo> explainInfo) {
            this.explainInfo = explainInfo;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        @Override
        public String toString() {
            return "RecordSimilarityResult.RecordSimilarityResultBuilder(super=" + super.toString() + ", score=" + this.score + ", left=" + this.left + ", right=" + this.right + ", explainInfo=" + this.explainInfo + ")";
        }
    }

    @JsonPOJOBuilder(withPrefix = "", buildMethodName = "build")
    static final class RecordSimilarityResultBuilderImpl extends RecordSimilarityResult.RecordSimilarityResultBuilder<RecordSimilarityResult, RecordSimilarityResult.RecordSimilarityResultBuilderImpl> {
        private RecordSimilarityResultBuilderImpl() {
        }

        protected RecordSimilarityResult.RecordSimilarityResultBuilderImpl self() {
            return this;
        }

        public RecordSimilarityResult build() {
            return new RecordSimilarityResult(this);
        }
    }
}
