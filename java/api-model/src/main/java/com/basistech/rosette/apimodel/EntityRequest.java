/******************************************************************************
 ** Copyright (c) 2014-2015 Basis Technology Corporation.
 **
 ** Licensed under the Apache License, Version 2.0 (the "License");
 ** you may not use this file except in compliance with the License.
 ** You may obtain a copy of the License at
 **
 **     http://www.apache.org/licenses/LICENSE-2.0
 **
 ** Unless required by applicable law or agreed to in writing, software
 ** distributed under the License is distributed on an "AS IS" BASIS,
 ** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ** See the License for the specific language governing permissions and
 ** limitations under the License.
 ******************************************************************************/

package com.basistech.rosette.apimodel;

/**
 * Class that represents the data from an entity extraction request
 */
public final class EntityRequest extends Request {

    private EntityOptions options;
    
    /**
     * Constructor for {@code EntityRequest}
     * @param language language code
     * @param content raw data
     * @param contentUri uri pointing to the data
     * @param contentType byte array of data
     * @param unit input unit code
     * @param options entity extraction options
     */
    public EntityRequest(
            String language,
            String content,
            String contentUri,
            String contentType,
            InputUnit unit,
            EntityOptions options
    ) {
        super(language, content, contentUri, contentType, unit);
        this.options = options;
    }

    /**
     * get the entity extraction options
     * @return the entity extraction options
     */
    public EntityOptions getOptions() {
        return options;
    }

    /**
     * set the entity extraction options
     * @param options the entity extraction options
     */
    public void setOptions(EntityOptions options) {
        this.options = options;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code EntityRequest}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntityRequest)) {
            return false;
        }

        EntityRequest that = (EntityRequest) o;
        return super.equals(o)
                && options != null ? getOptions().equals(that.getOptions()) : that.options == null;
    }
}
