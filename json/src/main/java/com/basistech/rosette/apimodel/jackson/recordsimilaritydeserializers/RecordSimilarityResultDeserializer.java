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
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityExplainInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityFieldInfo;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResult;
import com.basistech.rosette.apimodel.recordsimilarity.records.RecordSimilarityField;

public class RecordSimilarityResultDeserializer extends StdDeserializer<RecordSimilarityResult> {

    public RecordSimilarityResultDeserializer() {
        super(RecordSimilarityResult.class);
    }

    @Override
    public RecordSimilarityResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final Map<String, RecordSimilarityFieldInfo> fields = node.get("fields") != null ? node.get("fields").traverse(jsonParser.getCodec()).readValueAs(new TypeReference<Map<String, RecordSimilarityFieldInfo>>() { }) : null;
        final Double score = node.get("score") != null ? node.get("score").traverse(jsonParser.getCodec()).readValueAs(Double.class) : null;
        final RecordSimilarityExplainInfo explainInfo = node.get("explainInfo") != null ? node.get("explainInfo").traverse(jsonParser.getCodec()).readValueAs(RecordSimilarityExplainInfo.class) : null;
        Map<String, RecordSimilarityField> left = node.get("left") != null && fields != null ? RecordSimilarityDeserializerUtilities.parseRecord(node.get("left"), fields, jsonParser) : null;
        Map<String, RecordSimilarityField> right = node.get("right") != null && fields != null ? RecordSimilarityDeserializerUtilities.parseRecord(node.get("right"), fields, jsonParser) : null;
        final String error = node.get("error") != null ? node.get("error").traverse(jsonParser.getCodec()).readValueAs(String.class) : null;
        final String info = node.get("info") != null ? node.get("info").traverse(jsonParser.getCodec()).readValueAs(String.class) : null;
        return RecordSimilarityResult.builder()
                .score(score)
                .left(left)
                .right(right)
                .explainInfo(explainInfo)
                .error(error)
                .info(info)
                .build();
    }
}
