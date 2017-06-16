/*
* Copyright 2014 Basis Technology Corp.
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

package com.basistech.rosette.api;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public abstract class AbstractTest extends Assert {
    public static final String INFO_REPONSE = "{ \"buildNumber\": \"6bafb29d\", \"buildTime\": \"2015.10.08_10:19:26\", \"name\": "
            + "\"RosetteAPI\", \"version\": \"0.7.0\", \"versionChecked\": true }";
    protected static int serverPort;
    protected static ObjectMapper mapper;

    @BeforeClass
    public static void before() throws IOException {
        URL url = Resources.getResource("MockServerClientPort.property");
        String clientPort = Resources.toString(url, Charsets.UTF_8);
        serverPort = Integer.parseInt(clientPort);
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected static byte[] gzip(String text) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream out = new GZIPOutputStream(baos)) {
            out.write(text.getBytes(StandardCharsets.UTF_8));
        }
        return baos.toByteArray();
    }


}
