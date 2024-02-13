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

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuperBuilder
@ToString
@EqualsAndHashCode
public abstract class NameField implements RecordSimilarityField {
    @NotNull private final String text;

    @Jacksonized
    @SuperBuilder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Getter
    public static class UnfieldedName extends NameField {
        @JsonValue public String toJson() {
            return super.getText();
        }
    }

    @Jacksonized
    @SuperBuilder
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldedName extends NameField {
        private final String entityType;
        private final LanguageCode language;
        private final LanguageCode languageOfOrigin;
        private final ISO15924 script;
    }

}
