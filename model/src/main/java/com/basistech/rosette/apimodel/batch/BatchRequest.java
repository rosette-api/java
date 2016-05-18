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

/**
 * Define a batch job. Serialize this object to json to create a file
 * that requests a job.
 */
public class BatchRequest {
    // user-specified ID for the overall batch.
    private final String batchId;
    // URL to post when the entire job is complete.
    private final String completionCallbackUrl;
    // Define the items that make up the batch.
    private final BatchRequestItem[] items;
    // URL to output the batch results
    private final String batchOutputUrl;

    public BatchRequest(String batchId, String completionCallbackUrl, BatchRequestItem[] items, String batchOutputUrl) {
        this.batchId = batchId;
        this.completionCallbackUrl = completionCallbackUrl;
        this.items = items;
        this.batchOutputUrl = batchOutputUrl;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getCompletionCallbackUrl() {
        return completionCallbackUrl;
    }

    public BatchRequestItem[] getItems() {
        return items;
    }

    public String getBatchOutputUrl() {
        return batchOutputUrl;
    }
}
