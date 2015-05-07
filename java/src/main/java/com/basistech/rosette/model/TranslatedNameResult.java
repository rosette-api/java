package com.basistech.rosette.model;

public class TranslatedNameResult {

    private String sourceScript;
    private String sourceLanguageOfOrigin;
    private String sourceLanguageOfUse;
    private String targetLanguage;
    private String targetScript;
    private TransliterationScheme targetScheme;
    private String translation;
    private double confidence;

    public TranslatedNameResult() {}

    public TranslatedNameResult(
            String sourceScript,
            String sourceLanguageOfOrigin,
            String sourceLanguageOfUse,
            String translation,
            String targetLanguage,
            String targetScript,
            TransliterationScheme targetScheme,
            double confidence
    ) {
        this.sourceScript = sourceScript;
        this.sourceLanguageOfOrigin = sourceLanguageOfOrigin;
        this.sourceLanguageOfUse = sourceLanguageOfUse;
        this.translation = translation;
        this.targetLanguage = targetLanguage;
        this.targetScript = targetScript;
        this.targetScheme = targetScheme;
        this.confidence = confidence;
    }

    // Getter

    public String getTranslation() {
        return translation;
    }

    public String getSourceScript() {
        return sourceScript;
    }

    public String getSourceLanguageOfOrigin() {
        return sourceLanguageOfOrigin;
    }

    public String getSourceLanguageOfUse() {
        return sourceLanguageOfUse;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getTargetScript() {
        return targetScript;
    }

    public TransliterationScheme getTargetScheme() {
        return targetScheme;
    }

    public double getConfidence() {
        return confidence;
    }
}
