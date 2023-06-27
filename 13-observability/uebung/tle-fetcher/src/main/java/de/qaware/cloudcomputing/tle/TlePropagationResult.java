package de.qaware.cloudcomputing.tle;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
public class TlePropagationResult {

    @JsonbProperty("@context")
    private String context;

    @JsonbProperty("@id")
    private String id;

    @JsonbProperty("@type")
    private String type;

    @JsonbProperty("tle")
    private TleMember tleMember;

    private String algorithm;

    private OrbitalVector vector;

    private Geodetic geodetic;

    private TleParameters parameters;
}
