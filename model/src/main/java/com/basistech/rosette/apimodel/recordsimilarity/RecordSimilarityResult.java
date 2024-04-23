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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import com.basistech.rosette.apimodel.recordsimilarity.records.RecordSimilarityField;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordSimilarityResult {
    Double score;
    Map<String, RecordSimilarityField> left;
    Map<String, RecordSimilarityField> right;
    RecordSimilarityExplainInfo explainInfo;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<String> info;

    String error;
}
