package com.basistech.rosette.model;

public final class SentOptions {
    private String model = SentimentModel.REVIEW;
    private Boolean explain;

    public SentOptions() {}

    public SentOptions(
            String model,
            Boolean explain) {
        this.model = model;
        this.explain = explain;
    }

    public String getModel() {
        return model;
    }

    public Boolean getExplain() {
        return explain;
    }

}
