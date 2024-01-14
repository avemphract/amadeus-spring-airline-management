package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.entities.Flight;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.models.requests.flight.UpdateFlightRequest;
import com.katafrakt.airlinemanagement.models.responses.flight.FlightSearchResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IFlightService {
    Flight getFlightById(int id);

    List<Flight> getAllFlights();

    Flight updateFlight(UpdateFlightRequest updateFlightRequest) throws ResponseStatusException;

    Flight createFlight(CreateFlightRequest createFlightRequest);

    FlightSearchResponse search(String departureCity, String destinationCity, String beginDate, String returnDate);
}
