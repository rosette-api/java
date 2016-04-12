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
 * Class that represents the data from an entity resolution request
 */
@Deprecated
public final class LinkedEntitiesRequest extends Request {

    /**
     * constructor for {@code LinkedEntityRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param genre genre
     */
    protected LinkedEntitiesRequest(
            LanguageCode language,
            String genre,
            Object content,
            String contentUri,
            String contentType) {
        super(language, genre, content, contentUri, contentType);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * if the param is a {@code MorphologyRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedEntitiesRequest)) {
            return false;
        }

        return super.equals(o);
    }

    /**
     * Fluent builder class for {@link LinkedEntitiesRequest}.
     */
    public static class Builder extends Request.Builder<LinkedEntitiesRequest, Void, LinkedEntitiesRequest.Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public LinkedEntitiesRequest build() {
            return new LinkedEntitiesRequest(language, genre, content, contentUri, contentType);
        }
    }
}
