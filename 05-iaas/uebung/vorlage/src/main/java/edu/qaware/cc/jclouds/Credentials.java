package edu.qaware.cc.jclouds;

public enum Credentials {

    USER("AKCAJX6PXHO6XGT5DX5A"),
    KEY("U1AO7cq3zdU+S8iBN84Cloh1ggI1hWklq0iWbWml"),
    PROVIDER("aws-ec2");

    private final String value;

    Credentials(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }

}
