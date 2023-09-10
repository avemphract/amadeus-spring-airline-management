package com.katafrakt.airlinemanagement.models.requests.flight;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

public class UpdateFlightRequest {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private Integer departureAirportId;

    @Getter @Setter
    private Integer destinationAirportId;

    @Getter @Setter
    private OffsetDateTime beginDate;

    @Getter @Setter
    private OffsetDateTime endDate;

    @Getter @Setter
    private Double price;
}
