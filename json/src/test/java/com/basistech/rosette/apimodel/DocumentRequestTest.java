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

package com.basistech.rosette.apimodel;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DocumentRequestTest {

    @Test
    void getContentString() {
        String content = "expected";
        DocumentRequest<EntitiesOptions> request = DocumentRequest.<EntitiesOptions>builder()
                .content(content)
                .build();
        assertEquals(content, request.getContent());
        assertEquals(content, request.getRawContent());
        assertNull(request.getContentBytes());
    }

    @Test
    void getContentBytes() {
        String json = "{\"content\": \"My JSON content.\", \"language\": \"eng\"}";
        byte[] content = json.getBytes(StandardCharsets.UTF_8);
        DocumentRequest<EntitiesOptions> request = DocumentRequest.<EntitiesOptions>builder()
                .content(content, "application/json")
                .build();

        var actualRawContentInputStream = (ByteArrayInputStream) request.getRawContent();
        var actualRawContent = actualRawContentInputStream.readAllBytes();

        assertArrayEquals(content, actualRawContent);
        assertNull(request.getContent());
    }

    @Test
    void getContentInputStream() {
        InputStream content = new ByteArrayInputStream("expected".getBytes(StandardCharsets.UTF_8));
        DocumentRequest<EntitiesOptions> request = DocumentRequest.<EntitiesOptions>builder()
                .content(content, "text/plain")
                .build();
        assertEquals(content, request.getContentBytes());
        assertNull(request.getContent());
        assertEquals(content, request.getRawContent());
    }
}