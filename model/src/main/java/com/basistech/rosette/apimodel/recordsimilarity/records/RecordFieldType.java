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

package com.basistech.rosette.apimodel.recordsimilarity.records;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonDeserialize(using = RecordFieldType.RecordFieldTypeDeserializer.class)
public enum RecordFieldType {
    NAME("rni_name"),
    DATE("rni_date"),
    ADDRESS("rni_address"),
    UNKNOWN(null);

    @JsonValue private String value;

    public static class RecordFieldTypeDeserializer extends JsonDeserializer<RecordFieldType> {
        private RecordFieldType fromString(String value) {
            RecordFieldType type;
            switch (value) {
            case "rni_name":
                type = NAME;
                break;
            case "rni_date":
                type = DATE;
                break;
            case "rni_address":
                type = ADDRESS;
                break;
            default:
                type = UNKNOWN;
                type.value = value;
            }
            return type;
        }
        @Override
        public RecordFieldType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
            String value = jsonParser.getValueAsString();
            return fromString(value);
        }
    }
}
