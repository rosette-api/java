/*
 * Copyright 2026 Babel Street Rosette Ltd.
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

import com.basistech.rosette.apimodel.recordsimilarity.records.StringField;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringFieldDeserializer extends StdDeserializer<StringField> {
    public StringFieldDeserializer() {
        super(StringField.class);
    }

    @Override
    public StringField deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final List<String> data = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode element : node) {
                data.add(element.textValue());
            }
        } else {
            data.add(node.textValue());
        }
        return StringField.builder().data(data).build();
    }
}
