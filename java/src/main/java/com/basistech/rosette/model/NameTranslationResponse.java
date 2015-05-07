package com.basistech.rosette.model;

/* Simple api response data model for RNT */
public final class NameTranslationResponse extends Response {
    private TranslatedNameResult result;

    public NameTranslationResponse() {
    }

    public NameTranslationResponse(String requestId,
                                   TranslatedNameResult result
    ) {
        super(requestId);
        this.result = result;
    }

    public TranslatedNameResult getResult() {
        return result;
    }
}