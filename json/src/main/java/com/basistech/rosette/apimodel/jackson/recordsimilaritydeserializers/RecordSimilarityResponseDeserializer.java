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

import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResponse;
import com.basistech.rosette.apimodel.recordsimilarity.RecordSimilarityResult;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RecordSimilarityResponseDeserializer extends StdDeserializer<RecordSimilarityResponse> {

    public RecordSimilarityResponseDeserializer() {
        super(RecordSimilarityResponse.class);
    }

    @Override
    public RecordSimilarityResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        List<String> info = Optional.ofNullable(node.get("info"))
                .map(jsonNode -> StreamSupport.stream(jsonNode.spliterator(), false)
                        .map(JsonNode::asText)
                        .collect(Collectors.toList()))
                .orElse(null);
        String errorMessage = Optional.ofNullable(node.get("errorMessage")).map(JsonNode::asText).orElse(null);

        JsonNode resultsNode = node.get("results");
        List<RecordSimilarityResult> results = new ArrayList<>();
        if (resultsNode != null) {
            for (JsonNode resultNode : resultsNode) {
                results.add(RecordSimilarityDeserializerUtilities.parseResult(resultNode, jsonParser));
            }
        }
        return RecordSimilarityResponse.builder()
                .results(results)
                .info(info)
                .errorMessage(errorMessage)
                .build();
    }
}
