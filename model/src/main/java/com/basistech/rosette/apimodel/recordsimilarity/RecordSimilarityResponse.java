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
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import com.basistech.rosette.apimodel.Response;

import javax.validation.Valid;

/**
 * Response data model for comparison of two records.
 */
@Value
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordSimilarityResponse extends Response {
    @Valid Map<String, RecordSimilarityFieldInfo> fields;
    /**
     * @return list of record match results
     */
    @Valid List<RecordSimilarityResult> results;
    /**
     * @return error message to user in case no results matched the threshold
     */
    @Valid String errorMessage;
}
