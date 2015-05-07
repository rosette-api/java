package com.basistech.rosette.model;

public final class SentimentOptions {
    private String model = SentimentModel.REVIEW;
    private Boolean explain = false;

    public SentimentOptions() {}

    public SentimentOptions(
            String model,
            Boolean explain) {
        this.model = model;
        this.explain = explain;
    }

    public SentimentOptions(
            String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public Boolean getExplain() {
        return explain;
    }

}
