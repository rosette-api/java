package com.basistech.rosette.model;

public final class CategoryOptions {
    private String model;
    private Boolean explain = false;
    private int numCategories = 1;

    public CategoryOptions() {}

    public CategoryOptions(
            String model,
            Boolean explain,
            int numCategories
    ) {
        this.model = model;
        this.explain = explain;
        this.numCategories = numCategories;
    }

    public CategoryOptions(
            String model,
            Boolean explain
    ) {
        this.model = model;
        this.explain = explain;
    }

    public CategoryOptions(
            String model
    ) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public Boolean getExplain() {
        return explain;
    }

    public Integer getNumCategories() {
        return numCategories;
    }
}
