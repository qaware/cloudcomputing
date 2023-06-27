package de.qaware.cloudcomputing.tle;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.util.Date;

@Data
public class TleMember {

    @JsonbProperty("@context")
    private String context;

    @JsonbProperty("@id")
    private String id;

    @JsonbProperty("@type")
    private String type;

    private int satelliteId;

    private String name;

    private Date date;

    private String line1;

    private String line2;
}
