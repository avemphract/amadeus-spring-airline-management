package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.entities.Flight;

import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.models.requests.flight.UpdateFlightRequest;
import com.katafrakt.airlinemanagement.models.responses.flight.FlightSearchResponse;
import com.katafrakt.airlinemanagement.services.FlightService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SecurityRequirement(name = "Authorization")
@Tag(name ="Flights", description = "Get, modify or create flights, search flight options")
@RequestMapping("/flight")
@RestController
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @GetMapping(value = "/{id}")
    public Flight getFlight(@PathVariable int id){
       return flightService.getFlightById(id);
    }

    @PutMapping
    public Flight updateFlight(@RequestBody UpdateFlightRequest updateFlightRequest){
        return flightService.updateFlight(updateFlightRequest);
    }

    @PostMapping
    public Flight addFlight(@RequestBody CreateFlightRequest flight){
        return flightService.createFlight(flight);
    }

    @GetMapping("/search")
    public FlightSearchResponse search(@RequestParam String departureCity, @RequestParam String destinationCity, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam String beginDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String returnDate){
        return flightService.search(departureCity,destinationCity, beginDate, returnDate);
    }

}
