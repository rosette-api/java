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
 * Class that represents the data from an entity extraction request
 */
public final class EntitiesRequest extends Request {

    private EntitiesOptions options;
    
    /**
     * Constructor for {@code EntitiesRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param genre the genre
     * @param options entity extraction options
     */
    protected EntitiesRequest(
            LanguageCode language,
            String genre,
            Object content,
            String contentUri,
            String contentType,
            EntitiesOptions options
    ) {
        super(language, genre, content, contentUri, contentType);
        this.options = options;
    }

    /**
     * get the entity extraction options
     * @return the entity extraction options
     */
    public EntitiesOptions getOptions() {
        return options;
    }


    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code EntitiesRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntitiesRequest)) {
            return false;
        }

        EntitiesRequest that = (EntitiesRequest) o;
        return super.equals(o)
                && options != null ? getOptions().equals(that.getOptions()) : that.options == null;
    }

    /**
     * Fluent builder class for {@link EntitiesRequest}.
     */
    public static class Builder extends BaseBuilder<EntitiesRequest, EntitiesOptions, Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public EntitiesRequest build() {
            return new EntitiesRequest(language, genre, content, contentUri, contentType, options);
        }
    }
}
