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
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Value
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordSimilarityRequest extends Request {
    @NotEmpty @Valid Map<String, RecordSimilarityFieldInfo> fields;
    RecordSimilarityProperties properties;
    @NotNull @Valid RecordSimilarityRecords records;
}
