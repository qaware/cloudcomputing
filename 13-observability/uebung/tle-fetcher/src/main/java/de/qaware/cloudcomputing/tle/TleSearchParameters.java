package de.qaware.cloudcomputing.tle;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;

@Getter
@ToString()
public class TleSearchParameters {

    private static final int PAGE_MIN_VALUE = 1;
    private static final int PAGE_SIZE_MIN_VALUE = 1;
    private static final int PAGE_SIZE_MAX_VALUE = 100;

    @QueryParam("search")
    private String search;

    @QueryParam("sort")
    private TleSortColumn sort;

    @QueryParam("sort-dir")
    private TleSortDirection sortDirection;

    @Min(PAGE_MIN_VALUE)
    @QueryParam("page")
    private Integer page;

    @Min(PAGE_SIZE_MIN_VALUE)
    @Max(PAGE_SIZE_MAX_VALUE)
    @QueryParam("page-size")
    private Integer pageSize;

    @QueryParam("eccentricity[gte]")
    private BigDecimal eccentricityGte;

    @QueryParam("eccentricity[lte]")
    private BigDecimal eccentricityLte;

    @QueryParam("inclination[lt]")
    private BigDecimal inclinationLt;

    @QueryParam("inclination[gt]")
    private BigDecimal inclinationGt;

    @QueryParam("period[lt]")
    private BigDecimal periodLt;

    @QueryParam("period[gt]")
    private BigDecimal periodGt;
}
