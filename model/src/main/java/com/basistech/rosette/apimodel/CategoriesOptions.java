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

import javax.validation.constraints.Min;

/**
 * Categorization options 
 */
public final class CategoriesOptions extends Options {

    @Min(1)
    private Integer numCategories;

    /**
     * Create a set of categorization options with default values.
     * Note that {@code null} is used to represent defaults.
     */
    public CategoriesOptions() {
        //
    }

    /**
     * constructor for {@code CategoriesOptions}
     * @param numCategories max number of categories
     */
    public CategoriesOptions(
            Integer numCategories
    ) {
        this.numCategories = numCategories;
    }
    
    /**
     * get max number of categories 
     * @return number of categories
     */
    public Integer getNumCategories() {
        return numCategories;
    }

    /**
     * set max number of categories
     * @param numCategories number of categories
     */
    public void setNumCategories(Integer numCategories) {
        this.numCategories = numCategories;
    }

    public int hashCode() {
        return numCategories != null ? numCategories.hashCode() : 0;
    }

    /**
     * if the param is a {@code CategoriesOptions}, compare contents for equality
     * @param o the object
     * @return whether or not the param object is equal to this object
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CategoriesOptions)) {
            return false;
        }

        CategoriesOptions that = (CategoriesOptions) o;
        return numCategories != null ? numCategories.equals(that.getNumCategories()) : that.numCategories == null;
    }
}
