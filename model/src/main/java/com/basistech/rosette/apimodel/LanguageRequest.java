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

package com.basistech.rosette.apimodel;

import com.basistech.util.LanguageCode;

/**
 * Data from a RosetteAPI client languages detection request
 */
public final class LanguageRequest extends Request {

    private LanguageOptions options;
    
    /**
     * constructor for {@code LanguageRequest}
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param genre genre
     * @param options languages detection options
     */
    protected LanguageRequest(
            String genre,
            Object content,
            String contentUri,
            String contentType,
            LanguageOptions options
    ) {
        super(null, genre, content, contentUri, contentType);
        this.options = options;
    }

    /**
     * get the languages detection options
     * @return the languages detection options
     */
    public LanguageOptions getOptions() {
        return options;
    }

    /**
     * set the languages detection options
     * @param options the languages detection options
     */
    public void setOptions(LanguageOptions options) {
        this.options = options;
    }

    @Override
    public int hashCode() {
        return options != null ? options.hashCode() : 0;
    }

    /**
     * if the param is a {@code LanguageRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LanguageRequest)) {
            return false;
        }

        LanguageRequest that = (LanguageRequest) o;
        return super.equals(o)
                && options != null ? options.equals(that.options) : that.options == null;
    }

    /**
     * Fluent builder class for {@link LanguageRequest}.
     * Note that calls to {@link com.basistech.rosette.apimodel.Request.Builder#language(LanguageCode)}
     * will be ignored.
     */
    public static class Builder extends Request.Builder<LanguageRequest, LanguageOptions, LanguageRequest.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LanguageRequest build() {
            return new LanguageRequest(genre, content, contentUri, contentType, options);
        }
    }
}
