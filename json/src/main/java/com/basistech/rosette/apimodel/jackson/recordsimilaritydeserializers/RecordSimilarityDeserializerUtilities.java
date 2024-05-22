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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityExplainInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityFieldInfo;
import com.basistech.rosette.apimodel.recordsimilarity.records.AddressField;
import com.basistech.rosette.apimodel.recordsimilarity.records.DateField;
import com.basistech.rosette.apimodel.recordsimilarity.records.NameField;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordFieldType;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordSimilarityField;
import com.basistech.rosette.apimodel.recordsimilarity.records.UnknownField;

final class RecordSimilarityDeserializerUtilities {

    private RecordSimilarityDeserializerUtilities() {
    }

    public static RecordSimilarityResult parseResult(JsonNode node, JsonParser jsonParser) throws IOException {
        final Double score = node.get("score") != null
                ? node.get("score").traverse(jsonParser.getCodec()).readValueAs(Double.class)
                : null;
        final RecordSimilarityExplainInfo explainInfo = node.get("explainInfo") != null
                ? node.get("explainInfo").traverse(jsonParser.getCodec()).readValueAs(RecordSimilarityExplainInfo.class)
                : null;
        final Map<String, RecordSimilarityField> left = node.get("left") != null
                ? parseRecordForResponse(node.get("left"), jsonParser)
                : null;
        final Map<String, RecordSimilarityField> right = node.get("right") != null
                ? parseRecordForResponse(node.get("right"), jsonParser)
                : null;

        List<String> errorList = Optional.ofNullable(node.get("error"))
                .map(jsonNode -> StreamSupport.stream(jsonNode.spliterator(), false)
                        .map(JsonNode::asText)
                        .collect(Collectors.toList()))
                .orElse(null);
        final List<String> info = Optional.ofNullable(node.get("info"))
                .map(jsonNode -> StreamSupport.stream(jsonNode.spliterator(), false)
                        .map(JsonNode::asText)
                        .collect(Collectors.toList()))
                .orElse(null);
        return RecordSimilarityResult.builder()
                .score(score)
                .left(left)
                .right(right)
                .explainInfo(explainInfo)
                .error(errorList)
                .info(info)
                .build();
    }

    static Map<String, RecordSimilarityField> parseRecordForResponse(JsonNode jsonNode, JsonParser jsonParser) {
        final Map<String, RecordSimilarityField> recordMap = new HashMap<>();
        jsonNode.fields().forEachRemaining(entry -> {
            String fieldName = entry.getKey();
            try {
                recordMap.put(fieldName, jsonNode.get(fieldName).traverse(jsonParser.getCodec())
                        .readValueAs(UnknownField.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return recordMap;
    }

    static Map<String, RecordSimilarityField> parseRecord(
            JsonNode jsonNode,
            JsonParser jsonParser,
            @NotNull @Valid Map<String, RecordSimilarityFieldInfo> fields
    ) throws IOException {
        final Iterator<Map.Entry<String, JsonNode>> recordsIterator = jsonNode.fields();
        final Map<String, RecordSimilarityField> recordMap = new HashMap<>();
        while (recordsIterator.hasNext()) {
            final Map.Entry<String, JsonNode> recordEntry = recordsIterator.next();
            final String fieldName = recordEntry.getKey();
            final JsonNode fieldValue = recordEntry.getValue();

            final RecordSimilarityField fieldData;

            if (fields.containsKey(fieldName)) {
                final RecordSimilarityFieldInfo fieldInfo = fields.get(fieldName);
                if (fieldInfo.getType() == null) {
                    throw new IllegalArgumentException("Unspecified field type for: " + fieldName);
                }
                switch (fieldInfo.getType().toLowerCase(Locale.ENGLISH)) {
                case RecordFieldType.RNI_DATE:
                    fieldData = fieldValue.traverse(jsonParser.getCodec()).readValueAs(DateField.class);
                    break;
                case RecordFieldType.RNI_NAME:
                    fieldData = fieldValue.traverse(jsonParser.getCodec()).readValueAs(NameField.class);
                    break;
                case RecordFieldType.RNI_ADDRESS:
                    fieldData = fieldValue.traverse(jsonParser.getCodec()).readValueAs(AddressField.class);
                    break;
                default:
                    fieldData = fieldValue.traverse(jsonParser.getCodec()).readValueAs(UnknownField.class);
                }
            } else {
                //treat unmapped field as UnknownField so we can get to scoring,
                //it won't be counted toward the score anyway
                fieldData = fieldValue.traverse(jsonParser.getCodec()).readValueAs(UnknownField.class);
            }
            recordMap.put(fieldName, fieldData);
        }
        return recordMap;
    }
}
