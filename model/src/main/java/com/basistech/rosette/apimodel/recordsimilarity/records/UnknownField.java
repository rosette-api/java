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

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class UnknownField implements RecordSimilarityField {
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    JsonNode data;

    @JsonCreator
    public UnknownField(JsonNode data) {
        this.data = data;
    }

    @JsonValue
    public Object getData() {
        if (data == null) {
            return "";
        } else {
            if (data.isObject()) {
                Map<String, JsonNode> map = new LinkedHashMap<>();
                data.fields().forEachRemaining(fieldEntry -> map.put(fieldEntry.getKey(), fieldEntry.getValue()));
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(map);
                    return mapper.readTree(jsonString);
                } catch (JsonProcessingException e) {
                    return this.data;
                }
            }
            // if given input is not an Object node, it's a String so return it
            return this.data;
        }
    }

    //compare the content of the String form of the UnknownFields' data
    //enforce ordering
    @Override
    public boolean equals(Object o) {
        try {
            if (o instanceof UnknownField) {
                UnknownField other = (UnknownField) o;
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
                mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
                return mapper.writeValueAsString(this.data).equals(mapper.writeValueAsString(other.data));
            } else {
                return false;
            }
        } catch (JsonProcessingException ex) {
            return false;
        }
    }

    //hashcode based on String form
    @Override
    public int hashCode() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
            mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
            return 19 * mapper.writeValueAsString(this.data).hashCode();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
