package com.katafrakt.airlinemanagement.models.requests.airport;

import lombok.Getter;
import lombok.Setter;

public class CreateAirportRequest {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String city;
}
