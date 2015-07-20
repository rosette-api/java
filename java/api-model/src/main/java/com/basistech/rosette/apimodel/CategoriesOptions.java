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
public final class CategoriesOptions {

    private String model;
    private Boolean explain;
    private Integer numCategories;

    /**
     * constructor for {@code CategoriesOptions}
     * @param model model to use for categorization
     * @param explain whether to return explanation strings for each category returned
     * @param numCategories max number of categories
     */
    public CategoriesOptions(
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

    /**
     * set the model to use for categorization
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * set whether to return explanation strings for each category returned
     * @param explain whether to return explanation strings
     */
    public void setExplain(Boolean explain) {
        this.explain = explain;
    }

    /**
     * set max number of categories
     * @param numCategories number of categories
     */
    public void setNumCategories(Integer numCategories) {
        this.numCategories = numCategories;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (explain != null ? explain.hashCode() : 0);
        result = 31 * result + (numCategories != null ? numCategories.hashCode() : 0);
        return result;
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
        return model != null ? model.equals(that.getModel()) : that.model == null
                && explain != null ? explain.equals(that.getExplain()) : that.explain == null
                && numCategories != null ? numCategories.equals(that.getNumCategories()) : that.numCategories == null;
    }
}
