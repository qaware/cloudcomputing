package de.qaware.cloudcomputing.tle;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

@Data
public class TleSearchResult {

    @JsonbProperty("@context")
    private String context;

    @JsonbProperty("@id")
    private String id;

    @JsonbProperty("@type")
    private String type;

    private int totalItems;

    private List<TleMember> member;
}
