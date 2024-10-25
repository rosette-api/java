/*
* Copyright 2017-2024 Basis Technology Corp.
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

import com.basistech.rosette.annotations.JacksonMixin;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

/**
 * Define a batch job. Serialize this object to json to create a file
 * that requests a job.
 */
@Value
@Builder
@JacksonMixin
public final class BatchRequest {

    /**
     *
     * @return id an ID string.
     */
    @Builder.Default
    private final String batchId = UUID.randomUUID().toString();

    /**
     * A URL for Analytics API to call to inform you the completion of the batch.
     * It must be accessible on the Internet and can only be http: or https:.
     * Analytics API will use the GET method so optional parameters need to be
     * included as query parameters in the URL.
     *
     * @return completionCallbackUrl a URL for Analytics API to call when batch completes
     */
    private final String completionCallbackUrl;

    /**
     * @return list of batch request items
     */
    private final List<BatchRequestItem> items;

    /**
     * Specifies where the results should be stored. Only a valid AWS S3 URL
     * is supported at this time. The S3 bucket needs to have a proper policy
     * statement to grant read/write permission to Analytics API within the batch
     * processing time window. Analytics API's AWS account number is 625892746452.
     *
     * Example policy statement:
     *
     * <pre>
     *     <code>
     *         {
     *            "Version": "2012-10-17",
     *            "Id": "Policy1462373421611",
     *            "Statement": [
     *                {
     *                    "Sid": "Stmt1462373415064",
     *                    "Effect": "Allow",
     *                    "Principal": {
     *                        "AWS": "arn:aws:iam::625892746452:root"
     *                    },
     *                    "Action": "s3:ListBucket",
     *                    "Resource": "arn:aws:s3:::your-bucket-name",
     *                    "Condition": {
     *                        "StringLike": {
     *                           "s3:prefix": [
     *                               "output-folder-name/*"
     *                           ]
     *                        }
     *                    }
     *                },
     *                {
     *                    "Sid": "Stmt1462373415064",
     *                    "Effect": "Allow",
     *                    "Principal": {
     *                        "AWS": "arn:aws:iam::625892746452:root"
     *                    },
     *                    "Action": [
     *                        "s3:GetObject",
     *                        "s3:PutObject",
     *                        "s3:DeleteObject"
     *                    ],
     *                    "Resource": "arn:aws:s3:::your-bucket-name/output-folder-name/*"
     *                }
     *            ]
     *        }
     *     </code>
     * </pre>
     *
     * @return batchOutputUrl AWS S3 URL where results will be saved
     */
    private final String batchOutputUrl;
}
