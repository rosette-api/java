/*
* Copyright 2016 Basis Technology Corp.
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
package com.basistech.rosette.apimodel.batch;

import java.net.URL;

import com.basistech.rosette.apimodel.Response;

public final class BatchResponse extends Response {
    // batch id
    private final String id;
    // URL where the batch results will be stored
    private final URL batchOutputUrl;
    // progress checking endpoint/url
    private final URL batchCheckProgressUrl;

    /**
     * A response returned upon successful submission of a batch in which
     * contains a URL for progress checking and another specifies the output location.
     *
     * @param id ID of the batch
     * @param batchOutputUrl the URL of the processing results
     * @param batchCheckProgressUrl the URL that can be used to check batch progress
     */
    public BatchResponse(String id, URL batchOutputUrl, URL batchCheckProgressUrl) {
        this.id = id;
        this.batchOutputUrl = batchOutputUrl;
        this.batchCheckProgressUrl = batchCheckProgressUrl;
    }

    public String getId() {
        return id;
    }

    public URL getBatchOutputUrl() {
        return batchOutputUrl;
    }

    public URL getBatchCheckProgressUrl() {
        return batchCheckProgressUrl;
    }
}
