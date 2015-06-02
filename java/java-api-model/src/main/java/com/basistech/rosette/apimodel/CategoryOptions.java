/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2015 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
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
