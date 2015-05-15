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

package com.basistech.rosette.model;

import java.util.List;

/**
 *  Simple api response data model for categorization
 **/
public final class CategoryResponse extends Response {

    private List<Category> categories;
    
    public CategoryResponse() { super(); }

    /**
     * Constructor for {@code CategoryResponse}
     * @param requestId request id
     * @param categories list of categories
     */
    public CategoryResponse(String requestId,
                            List<Category> categories) {
        super(requestId);
        this.categories = categories;
    }

    /**
     * get the list of categories
     * @return the list of categories
     */
    public List<Category> getCategories() {
        return categories;
    }
}
