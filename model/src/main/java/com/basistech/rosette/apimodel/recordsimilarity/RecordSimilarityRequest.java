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
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.Valid;
import lombok.Value;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordSimilarityRequest extends Request {
    @NotEmpty @Valid Map<String, RecordSimilarityFieldInfo> fields;
    @Valid RecordSimilarityProperties properties;
    @NotNull @Valid RecordSimilarityRecords records;
    @Valid Map<String, String> parameters;
    @Valid String parameterUniverse;

    @Builder     // workaround for inheritance https://github.com/rzwitserloot/lombok/issues/853
    public RecordSimilarityRequest(String profileId,
                                   Map<String, RecordSimilarityFieldInfo> fields,
                                   RecordSimilarityProperties properties,
                                   RecordSimilarityRecords records,
                                   Map<String, String> parameters,
                                   String parameterUniverse) {
        super(profileId);
        this.fields = fields;
        this.properties = properties;
        this.records = records;
        this.parameters = parameters;
        this.parameterUniverse = parameterUniverse;

        if (this.parameters != null && parameterUniverse != null) {
            throw new IllegalArgumentException("Parameters and parameter universe may not both be specified");
        }
    }
}
