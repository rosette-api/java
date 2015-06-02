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
 * Categorization options 
 */
public final class CategoryOptions {

    private final String model;
    private final Boolean explain;
    private final Integer numCategories;

    /**
     * constructor for {@code CategoryOptions} 
     * @param model model to use for categorization
     * @param explain whether to return explanation strings for each category returned
     * @param numCategories max number of categories
     */
    public CategoryOptions(
            String model,
            Boolean explain,
            Integer numCategories
    ) {
        this.model = model;
        this.explain = explain;
        this.numCategories = numCategories;
    }
    
    /**
     * get the model to use for categorization 
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * get whether to return explanation strings for each category returned 
     * @return whether to return explanation strings
     */
    public Boolean getExplain() {
        return explain;
    }

    /**
     * get max number of categories 
     * @return number of categories
     */
    public Integer getNumCategories() {
        return numCategories;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (explain != null ? explain.hashCode() : 0);
        result = 31 * result + (numCategories != null ? numCategories.hashCode() : 0);
        return result;
    }

    /**
     * if the param is a {@code CategoryOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof CategoryOptions) {
            CategoryOptions that = (CategoryOptions) o;
            return model != null ? model.equals(that.getModel()) : that.model == null
                    && explain != null ? explain.equals(that.getExplain()) : that.explain == null
                    && numCategories != null ? numCategories.equals(that.getNumCategories()) : that.numCategories == null;
        } else {
            return false;
        }
    }
}
