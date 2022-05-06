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

package com.basistech.rosette.osgi.it;

import com.basistech.rosette.api.HttpRosetteAPI;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RosetteApiOsgiTest {
    @Test
    public void smokeTest() {
        HttpRosetteAPI api = new HttpRosetteAPI.Builder().build();
        assertNotNull(api);
    }
}
