package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.entities.Flight;

import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.models.requests.flight.UpdateFlightRequest;
import com.katafrakt.airlinemanagement.models.responses.flight.FlightSearchResponse;
import com.katafrakt.airlinemanagement.services.IFlightService;
import com.katafrakt.airlinemanagement.services.imp.FlightServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Authorization")
@Tag(name ="Flights", description = "Get, modify or create flights, search flight options")
@RequestMapping("/flight")
@RestController
public class FlightController {

    private final IFlightService flightService;

    @Autowired
    public FlightController(IFlightService flightService) {
        this.flightService = flightService;
    }

    @Operation(description = "Get all flights in db. Be careful, it could be too much flight in database.")
    @GetMapping
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @Operation(description = "Get a flight from flight id.")
    @GetMapping(value = "/{id}")
    public Flight getFlight(@PathVariable int id){
       return flightService.getFlightById(id);
    }

    @Operation(description = "Update a flight which id is given. Filled fields will have updated.")
    @PutMapping
    public Flight updateFlight(@RequestBody UpdateFlightRequest updateFlightRequest){
        return flightService.updateFlight(updateFlightRequest);
    }

    @Operation(description = "Create a flight.")
    @PostMapping
    public Flight addFlight(@RequestBody CreateFlightRequest flight){
        return flightService.createFlight(flight);
    }

    @Operation(description = "Search a flights departure to destination cities in beginDate and endDate ranges.")
    @GetMapping("/search")
    public FlightSearchResponse search(@RequestParam String departureCity, @RequestParam String destinationCity, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam String beginDate, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") String returnDate){
        return flightService.search(departureCity,destinationCity, beginDate, returnDate);
    }

}
