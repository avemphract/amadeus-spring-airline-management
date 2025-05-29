package com.katafrakt.airlinemanagement.models.requests.airport;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateAirportRequest {

    private Integer id;

    private String name;

    private String city;
}
