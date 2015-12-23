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

package com.basistech.rosette.apimodel;

import com.basistech.util.LanguageCode;

/**
 * Input data for the sentences endpoint.
 */
public class SentencesRequest extends Request {

    /**
     * Constructor for {@code Request}
     * Fields for the three ways content can come in:
     * 1. a "content" raw data
     * 2. a "contentUri" pointing to the data
     * 3. a "contentBytes" byte array for cases where data comes to us raw as an attachment
     *
     * @param language    language code
     * @param content     raw data
     * @param contentUri  uri pointing to the data
     * @param contentType byte array of data
     */
    protected SentencesRequest(LanguageCode language, Object content, String contentUri, String contentType) {
        super(language, content, contentUri, contentType);
    }

    /**
     * Fluent builder class for {@link SentencesRequest}.
     */
    public static class Builder extends Request.Builder<SentencesRequest, Void, SentencesRequest.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public SentencesRequest build() {
            return new SentencesRequest(language, content, contentUri, contentType);
        }
    }

}
