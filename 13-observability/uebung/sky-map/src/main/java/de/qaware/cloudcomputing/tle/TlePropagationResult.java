package de.qaware.cloudcomputing.tle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
