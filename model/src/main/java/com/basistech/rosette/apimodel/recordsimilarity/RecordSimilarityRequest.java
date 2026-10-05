/*
 * Copyright 2022 Babel Street Rosette Ltd.
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
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordSimilarityRequest extends Request {
    @Valid Map<String, RecordSimilarityFieldInfo> fields;
    @Valid RecordSimilarityProperties properties;
    @NotNull @Valid RecordSimilarityRecords records;
    @Valid RecordSimilarityComparisonMethod comparisonMethod;

    public RecordSimilarityRequest(String profileId,
                                   Map<String, RecordSimilarityFieldInfo> fields,
                                   RecordSimilarityProperties properties,
                                   RecordSimilarityRecords records) {
        this(profileId, fields, properties, records, RecordSimilarityComparisonMethod.ONE_TO_ONE);
    }

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public RecordSimilarityRequest(String profileId,
                                   Map<String, RecordSimilarityFieldInfo> fields,
                                   RecordSimilarityProperties properties,
                                   RecordSimilarityRecords records,
                                   RecordSimilarityComparisonMethod comparisonMethod) {
        super(profileId);
        this.fields = fields;
        this.properties = properties;
        this.records = records;
        this.comparisonMethod = comparisonMethod == null ? RecordSimilarityComparisonMethod.ONE_TO_ONE : comparisonMethod;
    }
}
