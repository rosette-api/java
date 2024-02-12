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

import com.basistech.util.LanguageCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class NameField implements RecordSimilarityField {
    @NotNull private String text;

    @NoArgsConstructor
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    public static class UnfieldedName extends NameField {
        public UnfieldedName(final String text) {
            super(text);
        }
        @JsonValue public String toJson() {
            return super.getText();
        }
    }

    @NoArgsConstructor
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldedName extends NameField {
        private String entityType;
        private LanguageCode language;

        public FieldedName(final String text, final String entityType, final LanguageCode language) {
            super(text);
            this.entityType = entityType;
            this.language = language;
        }
    }

}
