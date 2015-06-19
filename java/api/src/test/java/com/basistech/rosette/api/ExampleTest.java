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

package com.basistech.rosette.api;

import com.basistech.rosette.example.CategoriesExample;
import com.basistech.rosette.example.SentimentExample;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class ExampleTest {
    
    private String[] args;
    private OutputStream baos;
    private PrintStream tempPs; // temporary output stream
    private final PrintStream standardPs = System.out; // default output stream
    
    @BeforeClass
    public static void before() {
        String key = System.getProperty("rosette.api.key");
        if (key == null) {
            throw new RuntimeException("null API key");
        }
    }
    
    @Before
    public void setUp() {
        args = new String[]{};
        baos = new ByteArrayOutputStream();
        tempPs = new PrintStream(baos);
        System.setOut(tempPs);
    }
    
    @Test
    public void testCategoriesExample() throws URISyntaxException, IOException {
        // default URL
        CategoriesExample.main(args);
        String result = baos.toString();
        tempPs.flush();
        
        assertTrue(result.contains("TECHNOLOGY_AND_COMPUTING\t0.277765"));
        
        // custom URL
        String[] customArgs = new String[2];
        customArgs[0] = "-url";
        customArgs[1] = "http://xckd.com";
        CategoriesExample.main(customArgs);
        result = baos.toString();
        tempPs.flush();
        
        assertTrue(result.contains("ARTS_AND_ENTERTAINMENT\t0.136049"));
    }
    
    @Test
    public void testSentimentExample() throws URISyntaxException, IOException {
        // default file
        SentimentExample.main(args);
        String result = baos.toString();
        tempPs.flush();

        assertTrue(result.contains("pos\t0.624564\nneg\t0.375436"));
        
        // custom file
        ClassLoader cl = SentimentExample.class.getClassLoader();
        String[] customArgs = new String[2];
        customArgs[0] = "-file";
        customArgs[1] = "England.txt";
        SentimentExample.main(customArgs);
        result = baos.toString(); // still returns default?
        tempPs.flush();
    }
    
    @After
    public void tearDown() {
        tempPs.close();
        System.setOut(standardPs);
    }
}
