package com.basistech.rosette.model;

import java.util.List;

public class HanReadings {
    private String text;
    private List<String> hanReadings;

    public HanReadings() {}

    public HanReadings(
            String text,
            List<String> hanReadings
    ) {
        this.text = text;
        this.hanReadings = hanReadings;
    }

    public String getText() {
        return text;
    }

    public List<String> getHanReadings() {
        return hanReadings;
    }
}
