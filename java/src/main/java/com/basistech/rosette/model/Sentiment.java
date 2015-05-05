package com.basistech.rosette.model;

import java.util.List;

public class Sentiment {
    private String label;
    private double confidence;
    private List<String> explanations;

    public Sentiment() {}

    public Sentiment(
            String label,
            double confidence,
            List<String> explanations
    ) {
        this.label = label;
        this.confidence = confidence;
        this.explanations = explanations;
    }

    public String getLabel() {
        return label;
    }

    public double getConfidence() {
        return confidence;
    }

    public List<String> getExplanations() {
        return explanations;
    }
}
