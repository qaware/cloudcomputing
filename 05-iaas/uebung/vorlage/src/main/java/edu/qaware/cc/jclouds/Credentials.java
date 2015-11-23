package edu.qaware.cc.jclouds;

public enum Credentials {

    USER("<USER>"),
    KEY("<KEY>"),
    PROVIDER("aws-ec2");

    private final String value;

    Credentials(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
