package de.qaware.cloudcomputing.tle;

import lombok.Data;

@Data
public class OrbitalVector {

    private String referenceFrame;
    private Position position;
    private Velocity velocity;

}
