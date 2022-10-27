/*
* Copyright 2014-2022 Basis Technology Corp.
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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertFalse;

class NonNullTest {
    private ObjectMapper mapper;

    // All test resource filename has <ClassName>.json pattern. They contain null requestId and timers fields.
    private static Stream<Arguments> testNonNullParameters() throws IOException {
        File dir = new File("src/test/data");
        Stream.Builder<Arguments> streamBuilder = Stream.builder();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir.toPath())) {
            for (Path file : paths) {
                if (file.toString().endsWith(".json")) {
                    String className = file.getFileName().toString().replace(".json", "");
                    streamBuilder.add(Arguments.of(NonNullTest.class.getPackage().getName() + "." + className, file.toFile()));
                }
            }
        }
        return streamBuilder.build();
    }

    @BeforeEach
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @ParameterizedTest(name = "{0}; {1}")
    @MethodSource("testNonNullParameters")
    void testNonNull(String className, File testFile) throws IOException, ClassNotFoundException {
        Class<?> c = Class.forName(className);
        String s = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        String s2 = mapper.writeValueAsString(mapper.readValue(s, c));
        assertFalse(s2.contains("requestId"));
    }
}
