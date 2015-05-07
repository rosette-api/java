package com.basistech.rosette.model;

public class NameMatcherResult {

    private double score;

    public NameMatcherResult() {}

    public NameMatcherResult(
            double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }
}
