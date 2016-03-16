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
 * Input parameters for the morphology endpoint.
 */
public final class MorphologyRequest extends Request {

    private MorphologyOptions options;
    
    /**
     * constructor for {@code MorphologyRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param options morphology options
     * @param genre genre
     */
    protected MorphologyRequest(
            LanguageCode language,
            String genre,
            Object content,
            String contentUri,
            String contentType,
            MorphologyOptions options
    ) {
        super(language, genre, content, contentUri, contentType);
        this.options = options;
    }

    /**
     * get the base linguistics options
     * @return the base linguistics options
     */
    public MorphologyOptions getOptions() {
        return options;
    }

    /**
     * set the base linguistics options
     * @param options the base linguistics options
     */
    public void setOptions(MorphologyOptions options) {
        this.options = options;
    }

    @Override
    public int hashCode() {
        return options != null ? options.hashCode() : 0;
    }

    /**
     * if the param is a {@code MorphologyRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MorphologyRequest)) {
            return false;
        }

        MorphologyRequest that = (MorphologyRequest) o;
        return super.equals(o)
                && options != null ? options.equals(that.getOptions()) : that.options == null;
    }

    /**
     * Fluent builder class for {@link MorphologyRequest}.
     */
    public static class Builder extends Request.Builder<MorphologyRequest, MorphologyOptions, MorphologyRequest.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public MorphologyRequest build() {
            return new MorphologyRequest(language, genre, content, contentUri, contentType, options);
        }
    }
}
