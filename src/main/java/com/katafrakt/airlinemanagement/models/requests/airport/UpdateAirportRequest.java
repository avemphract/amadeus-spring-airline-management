package com.katafrakt.airlinemanagement.models.requests.airport;

import lombok.Getter;
import lombok.Setter;

public class UpdateAirportRequest {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String city;
}
