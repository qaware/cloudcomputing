package de.qaware.cloudcomputing.tle;

import lombok.Getter;

public enum TleSortDirection {

    ASC("asc"),
    DESC("desc");

    @Getter
    private String value;

    TleSortDirection(String value) {
        this.value = value;
    }
}
