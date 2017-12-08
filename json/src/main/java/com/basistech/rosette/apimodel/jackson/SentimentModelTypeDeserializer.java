/*
 * Copyright 2017 Basis Technology Corp.
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

package com.basistech.rosette.apimodel.jackson;

import com.basistech.rosette.apimodel.SentimentModelType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * A Jackson deserializer for {@link SentimentModelType}.
 */
public class SentimentModelTypeDeserializer extends StdDeserializer<SentimentModelType> {
    public SentimentModelTypeDeserializer() {
        super(SentimentModelType.class);
    }

    @Override
    public SentimentModelType deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {
        return SentimentModelType.forValue(jp.getText());
    }
}
