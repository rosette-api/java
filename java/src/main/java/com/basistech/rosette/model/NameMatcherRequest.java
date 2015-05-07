package com.basistech.rosette.model;

import com.basistech.rosette.api.RosetteAPIParameterException;

public class NameMatcherRequest {

    private Name name1;
    private Name name2;

    public NameMatcherRequest() {}

    public NameMatcherRequest(Name name1,
                              Name name2) throws RosetteAPIParameterException {
        if (name1.getText() == null || name1.getText().isEmpty()) {
            throw new RosetteAPIParameterException(new ErrorResponse(null, "missingParameter", "\"name1\" is required and cannot be blank"));
        }
        if (name2.getText() == null || name1.getText().isEmpty()) {
            throw new RosetteAPIParameterException(new ErrorResponse(null, "missingParameter", "\"name2\" is required and cannot be blank"));
        }
        this.name1 = name1;
        this.name2 = name2;
    }

    public Name getName1() {
        return name1;
    }

    public Name getName2() {
        return name2;
    }
}
