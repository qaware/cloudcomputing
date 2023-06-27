package de.qaware.cloudcomputing.tle;

import lombok.Getter;

public enum TleSortColumn {

    ID("id"),
    NAME("name"),
    POPULARITY("popularity"),
    INCLINATION("inclination"),
    ECCENTRICITY("eccentricity"),
    PERIOD("period");

    @Getter
    private final String value;

    TleSortColumn(String value) {
        this.value = value;
    }
}
