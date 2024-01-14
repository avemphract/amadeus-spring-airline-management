package com.katafrakt.airlinemanagement.models.requests.airport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CreateAirportRequest {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String city;
}
