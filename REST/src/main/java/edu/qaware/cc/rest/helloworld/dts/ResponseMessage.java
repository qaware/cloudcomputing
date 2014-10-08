package edu.qaware.cc.rest.helloworld.dts;

public class ResponseMessage {

    private String timestamp;
    private String message;

    public ResponseMessage(String timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}
