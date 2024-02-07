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

package com.basistech.rosette.apimodel.recordsimilarity;

import com.basistech.rosette.apimodel.Date;
import com.basistech.rosette.apimodel.IAddress;
import com.basistech.rosette.apimodel.Name;
import com.basistech.rosette.apimodel.RecordType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.basistech.rosette.apimodel.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.*;

//CHECKSTYLE:OFF
public class RecordSimilarityDeserializer extends StdDeserializer<RecordSimilarityRequest> {

    private static final Logger LOG = LoggerFactory.getLogger(RecordSimilarityDeserializer.class);

    public RecordSimilarityDeserializer() {
        super(RecordSimilarityRequest.class);
    }

    @Override
    public RecordSimilarityRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        LOG.info("MEGAN");
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        LOG.info(node.get("fields").toPrettyString());
        final Map<String, RecordSimilarityField> fields = node.get("fields").traverse(jsonParser.getCodec()).readValueAs(new TypeReference<Map<String, RecordSimilarityField>>() {});
        final RecordSimilarityProperties properties = node.get("properties").traverse(jsonParser.getCodec()).readValueAs(RecordSimilarityProperties.class);
        final RecordSimilarityRecords records = new RecordSimilarityRecords(
                parseRecords(node.get("records").get("left"), fields, jsonParser),
                parseRecords(node.get("records").get("right"), fields, jsonParser));
        return new RecordSimilarityRequest(fields, properties, records);

    }

    private static List<Map<String,Record>> parseRecords(final JsonNode arrayNode,
                                             final Map<String, RecordSimilarityField> fields,
                                             final JsonParser jsonParser) throws IOException {
        final List<Map<String,Record>> records = new ArrayList<>();
        for (JsonNode recordNode : arrayNode) {
            final Iterator<Map.Entry<String, JsonNode>> recordsIterator = recordNode.fields();
            final Map<String,Record> record = new HashMap<>();
            while (recordsIterator.hasNext()) {
                final Map.Entry<String, JsonNode> recordEntry = recordsIterator.next();
                final String recordName = recordEntry.getKey();
                final JsonNode recordValue = recordEntry.getValue();

                if (fields.containsKey(recordName)) {
                    final RecordType recordType = fields.get(recordName).getType();
                    final Record recordData;
                    switch (recordType) {
                        case DATE:
                            recordData = recordValue.traverse(jsonParser.getCodec()).readValueAs(Date.class);
                            break;
                        case NAME:
                            recordData = recordValue.traverse(jsonParser.getCodec()).readValueAs(Name.class);
                            break;
                        case ADDRESS:
                            recordData = recordValue.traverse(jsonParser.getCodec()).readValueAs(IAddress.class);
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported record type: " + recordType);
                    }
                    record.put(recordName, recordData);
                } else {
                    throw new IllegalArgumentException("Unsupported record name: " + recordName);
                }
            }
            records.add(record);
        }
        return records;
    }

}
