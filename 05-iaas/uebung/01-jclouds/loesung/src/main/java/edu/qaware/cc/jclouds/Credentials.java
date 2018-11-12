package edu.qaware.cc.jclouds;

public enum Credentials {

    USER("AKIAJIEQJACNUJNOU5TQ"),
    KEY("vrCsF4m/sp//nSPWFyJpsbg/Sq9gARLoLeIe1eJI"),
    PROVIDER("aws-ec2");

    private final String value;

    Credentials(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
