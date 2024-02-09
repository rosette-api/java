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

import com.basistech.rosette.apimodel.recordsimilarity.records.RecordAddress;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class RecordAddressDeserializer extends StdDeserializer<RecordAddress> {
    public RecordAddressDeserializer() {
        super(RecordAddress.class);
    }

    @Override
    public RecordAddress deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (node.isObject()) {
            return jsonParser.getCodec().treeToValue(node, RecordAddress.FieldedRecordAddress.class);
        } else if (node.isTextual()) {
            return new RecordAddress.UnfieldedRecordAddress(node.textValue());
        }
        throw new IOException("Invalid JSON structure: unexpected node type");
    }
}
