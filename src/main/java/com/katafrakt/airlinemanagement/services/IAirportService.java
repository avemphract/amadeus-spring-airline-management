package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.models.requests.airport.UpdateAirportRequest;

import java.util.List;

public interface IAirportService {
    List<Airport> getAllAirports();

    Airport getAirport(int id);

    Airport updateAirport(UpdateAirportRequest updateAirportRequestObject);

    Airport createAirport(CreateAirportRequest createAirportRequestObject);
}
