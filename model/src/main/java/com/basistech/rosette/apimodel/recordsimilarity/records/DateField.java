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

package com.basistech.rosette.apimodel.recordsimilarity.records;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;

@SuperBuilder
@Value
@NonFinal
public abstract class DateField implements RecordSimilarityField {
    @NotBlank String date;

    @Jacksonized
    @SuperBuilder
    @Value
    public static class UnfieldedDate extends DateField {
        @JsonValue public String toJson() {
            return super.getDate();
        }
    }

    @Jacksonized
    @SuperBuilder
    @Value
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldedDate extends DateField {
        @NotBlank String format;
    }

}
