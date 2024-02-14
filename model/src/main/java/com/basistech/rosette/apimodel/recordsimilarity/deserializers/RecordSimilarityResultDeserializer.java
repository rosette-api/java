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

package com.basistech.rosette.apimodel.recordsimilarity.deserializers;

import static com.basistech.rosette.apimodel.recordsimilarity.deserializers.RecordSimilarityRequestDeserializer.parseRecord;

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

public class RecordSimilarityResultDeserializer extends StdDeserializer<RecordSimilarityResult> {

    public RecordSimilarityResultDeserializer() {
        super(RecordSimilarityResult.class);
    }

    @Override
    public RecordSimilarityResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try (jsonParser) {
            final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            final Map<String, RecordSimilarityFieldInfo> fields = node.get("fields").traverse(jsonParser.getCodec()).readValueAs(new TypeReference<Map<String, RecordSimilarityFieldInfo>>() {
            });
            final Double score = node.get("score").traverse(jsonParser.getCodec()).readValueAs(Double.class);
            final Map<String, RecordSimilarityExplainInfo> explainInfo = node.get("explainInfo").traverse(jsonParser.getCodec()).readValueAs(new TypeReference<Map<String, RecordSimilarityExplainInfo>>() {
            });
            return RecordSimilarityResult.builder()
                    .score(score)
                    .left(parseRecord(node.get("left"), fields, jsonParser))
                    .right(parseRecord(node.get("right"), fields, jsonParser))
                    .explainInfo(explainInfo)
                    .build();
        }
    }
}
