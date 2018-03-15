package edu.qaware.cc.zwitscher.api.entities;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value = "Eine Nachricht - versendet mit Zwitscher")
public class ZwitscherMessage {
    private Date timestamp;
    private String message;
    
    public ZwitscherMessage(String message){
        this.message = message;
        this.timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @ApiModelProperty(value = "Versandzeitpunkt", required=true)
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    @ApiModelProperty(value = "Nachricht", required=true, allowableValues = "Yo!,yo!,YO!")
    public void setMessage(String message) {
        this.message = message;
    }
    
}