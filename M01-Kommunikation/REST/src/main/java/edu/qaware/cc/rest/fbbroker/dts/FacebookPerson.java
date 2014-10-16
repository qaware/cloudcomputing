package edu.qaware.cc.rest.fbbroker.dts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookPerson {
    private String name;
    private String id;

    public FacebookPerson() {
    }

    public FacebookPerson(
            String id,
            String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
