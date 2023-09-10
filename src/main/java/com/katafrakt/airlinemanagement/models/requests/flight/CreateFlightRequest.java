package com.katafrakt.airlinemanagement.models.requests.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class CreateFlightRequest {
    @Getter @Setter
    private int departureAirportId;

    @Getter @Setter
    private int destinationAirportId;

    @Getter @Setter
    private OffsetDateTime beginDate;

    @Getter @Setter
    private OffsetDateTime endDate;

    @Getter @Setter
    private double price;
}
