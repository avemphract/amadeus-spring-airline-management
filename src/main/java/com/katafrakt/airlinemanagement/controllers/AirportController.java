package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.models.requests.airport.UpdateAirportRequest;
import com.katafrakt.airlinemanagement.services.AirportService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "Authorization")
@Tag(name = "Airports", description = "Airport Controller")
@RequestMapping("/airport")
@RestController
public class AirportController {

    private final AirportService airportService;
    @Autowired
    public AirportController(AirportService airportService){
        this.airportService = airportService;
    }
    @GetMapping
    public List<Airport> getAllAirports(){
        return airportService.getAllAirports();
    }

    @GetMapping(value = "/{id}")
    public Airport getAirport(@PathVariable int id){
        return airportService.getAirport(id);
    }


    @PutMapping
    public Airport updateAirport(@RequestBody UpdateAirportRequest updateAirportRequestObject){
        return airportService.updateAirport(updateAirportRequestObject);
    }

    @PostMapping
    public Airport createAirport(@RequestBody CreateAirportRequest createAirportRequestObject){
        return airportService.createAirport(createAirportRequestObject);
    }
}
