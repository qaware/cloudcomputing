package edu.qaware.cc.rest.fbbroker.dts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookResponse {

    private FacebookMessage[] data;

    public FacebookResponse() {
    }

    public FacebookResponse(
            FacebookMessage[] data) {
        this.data = data;
    }

    public FacebookMessage[] getData() {
        return data;
    }
}
