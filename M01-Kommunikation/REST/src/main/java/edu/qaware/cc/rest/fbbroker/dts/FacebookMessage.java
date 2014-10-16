package edu.qaware.cc.rest.fbbroker.dts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookMessage {

    private String id;
    private String message;
    private String type;
    private FacebookPerson from;

    public FacebookMessage() {
    }

    public FacebookMessage(
            String id,
            String message,
            String type,
            FacebookPerson from) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from.getName();
    }
}
