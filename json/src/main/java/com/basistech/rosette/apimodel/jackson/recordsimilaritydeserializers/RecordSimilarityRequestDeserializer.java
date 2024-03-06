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

package com.basistech.rosette.apimodel.jackson.recordsimilaritydeserializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityFieldInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityProperties;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRecords;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityRequest;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordSimilarityField;

public class RecordSimilarityRequestDeserializer extends StdDeserializer<RecordSimilarityRequest> {

    public RecordSimilarityRequestDeserializer() {
        super(RecordSimilarityRequest.class);
    }

    @Override
    public RecordSimilarityRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try (jsonParser) {
            final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            final Map<String, RecordSimilarityFieldInfo> fields = node.get("fields") != null ? node.get("fields").traverse(jsonParser.getCodec()).readValueAs(new TypeReference<Map<String, RecordSimilarityFieldInfo>>() { }) : new HashMap<>();
            final RecordSimilarityProperties properties = node.get("properties") != null ? node.get("properties").traverse(jsonParser.getCodec()).readValueAs(RecordSimilarityProperties.class) : RecordSimilarityProperties.builder().build();
            RecordSimilarityRecords records = null;
            if (node.get("records") != null && fields != null && node.get("records").get("left") != null && node.get("records").get("right") != null) {
                records = RecordSimilarityRecords.builder()
                        .left(parseRecords(node.get("records").get("left"), fields, jsonParser))
                        .right(parseRecords(node.get("records").get("right"), fields, jsonParser))
                        .build();
            }
            return RecordSimilarityRequest.builder()
                    .fields(fields)
                    .properties(properties)
                    .records(records)
                    .build();
        }
    }

    private static List<Map<String, RecordSimilarityField>> parseRecords(final JsonNode arrayNode,
                                                                         final Map<String, RecordSimilarityFieldInfo> fields,
                                                                         final JsonParser jsonParser) throws IOException {
        final List<Map<String, RecordSimilarityField>> records = new ArrayList<>();
        for (JsonNode recordNode : arrayNode) {
            records.add(RecordSimilarityDeserializerUtilities.parseRecord(recordNode, fields, jsonParser));
        }
        return records;
    }
}
