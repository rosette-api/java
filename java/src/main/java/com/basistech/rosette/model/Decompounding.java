package com.basistech.rosette.model;

import java.util.List;

public class Decompounding {
    private String text;
    private List<String> compoundComponents;

    public Decompounding() {}

    public Decompounding(
            String text,
            List<String> compoundComponents
    ) {
        this.text = text;
        this.compoundComponents = compoundComponents;
    }

    public String getText() {
        return text;
    }

    public List<String> getCompoundComponents() {
        return compoundComponents;
    }
}
