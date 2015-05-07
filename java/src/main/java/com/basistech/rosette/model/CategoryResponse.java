package com.basistech.rosette.model;

import java.util.List;

public final class CategoryResponse extends Response {

    private List<Category> categories;

    public CategoryResponse() {}

    public CategoryResponse(String requestId,
                            List<Category> categories) {
        super(requestId);
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
