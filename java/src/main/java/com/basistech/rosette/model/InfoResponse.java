package com.basistech.rosette.model;

public class InfoResponse extends Response {
    private String name;
    private String version;
    private String buildNumber;
    private String buildTime;

    public InfoResponse() {

    }

    public InfoResponse(String name, String version, String buildNumber, String buildTime) {
        super(null);
        this.name = name;
        this.version = version;
        this.buildNumber = buildNumber;
        this.buildTime = buildTime;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public String getBuildTime() {
        return buildTime;
    }
}
