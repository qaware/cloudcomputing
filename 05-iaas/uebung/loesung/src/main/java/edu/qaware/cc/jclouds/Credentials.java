package edu.qaware.cc.jclouds;

public enum Credentials {
    
    SERVICE_USER("account-1@vlcc-1137.iam.gserviceaccount.com"),
    CERT_FILE("gce-cert.json");
    
    private final String value;
    
    Credentials(String value){
        this.value = value;
    }
    
    public String get(){
        return value;
    }
    
}