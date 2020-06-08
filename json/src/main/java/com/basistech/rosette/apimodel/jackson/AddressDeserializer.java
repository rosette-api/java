/*
 * Copyright 2020 Basis Technology Corp.
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

import com.basistech.rosette.apimodel.FieldedAddress;
import com.basistech.rosette.apimodel.IAddress;
import com.basistech.rosette.apimodel.UnfieldedAddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class AddressDeserializer extends StdDeserializer<IAddress> {

    private static final String UNFIELDED_KEY = "address";

    AddressDeserializer() {
        super(IAddress.class);
    }

    @Override
    public IAddress deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        TreeNode root = mapper.readTree(jp);
        Class<? extends IAddress> addressClass = FieldedAddress.class;
        if (root instanceof TextNode) {
            // We only have a JsonProperty-based constructor, so we can't
            // have jackson create the object for us.
            return new UnfieldedAddress(((TextNode) root).textValue());
        } else {
            Iterator<Map.Entry<String, JsonNode>> iterator = ((ObjectNode)root).fields();
            if (iterator.hasNext()) {
                Map.Entry<String, JsonNode> element = iterator.next();
                if (UNFIELDED_KEY.equals(element.getKey())) {
                    addressClass = UnfieldedAddress.class;
                }
            }
        }
        return mapper.treeToValue(root, addressClass);
    }
}
