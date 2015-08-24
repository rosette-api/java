/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.basistech.rosette.apimodel.jackson.ApiModelMixinModule;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Parameterized.class)
public class NonNullTest extends Assert {

    private final String className;
    private final File testFile;
    private ObjectMapper mapper;

    public NonNullTest(String className, File testFile) {
        this.className = className;
        this.testFile = testFile;
    }

    // All test resource filename has <ClassName>.json pattern. They contain null requestId and timers fields.
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws URISyntaxException, IOException {
        URL url = NonNullTest.class.getClassLoader().getResource(".");
        File dir = new File(url.toURI());
        Collection<Object[]> params = new ArrayList<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir.toPath())) {
            for (Path file : paths) {
                if (file.toString().endsWith(".json")) {
                    String className = file.getFileName().toString().replace(".json", "");
                    params.add(new Object[]{NonNullTest.class.getPackage().getName() + "." + className, file.toFile()});
                }
            }
        }
        return params;
    }

    @Before
    public void init() {
        mapper = ApiModelMixinModule.setupObjectMapper(new ObjectMapper());
    }

    @Test
    public void testNonNull() throws IOException, ClassNotFoundException {
        Class<?> c = Class.forName(className);
        String s = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
        String s2 = mapper.writeValueAsString(mapper.readValue(s, c));
        assertFalse(s2.contains("requestId"));
        assertFalse(s2.contains("timers"));
    }
}
