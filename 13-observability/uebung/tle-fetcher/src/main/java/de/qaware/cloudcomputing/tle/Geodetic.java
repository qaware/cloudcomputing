package de.qaware.cloudcomputing.tle;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Geodetic {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal altitude;
}
