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
 * Class that represents the data from a categorization request
 */
public final class CategoriesRequest extends Request {

    private CategoriesOptions options;

    /**
     * Constructor for {@code CategoriesRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param genre genre
     * @param options categorization options
      */
    protected CategoriesRequest(
            LanguageCode language,
            String genre,
            Object content,
            String contentUri,
            String contentType,
            CategoriesOptions options
    ) {
        super(language, genre, content, contentUri, contentType);
        this.options = options;
    }

    /**
     * get the categorization options
     * @return the categorization options
     */
    public CategoriesOptions getOptions() {
        return options;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code CategoriesRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CategoriesRequest)) {
            return false;
        }

        CategoriesRequest that = (CategoriesRequest) o;
        return super.equals(o)
                && options != null ? options.equals(that.getOptions()) : that.options == null;
    }

    /**
     * Fluent builder class for {@link CategoriesRequest}.
     */
    public static class Builder extends BaseBuilder<CategoriesRequest, CategoriesOptions, Builder> {
        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public CategoriesRequest build() {
            return new CategoriesRequest(language, genre, content, contentUri, contentType, options);
        }
    }
}
