package com.katafrakt.airlinemanagement.controllers;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.models.requests.airport.UpdateAirportRequest;
import com.katafrakt.airlinemanagement.services.IAirportService;
import com.katafrakt.airlinemanagement.services.imp.AirportServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "Authorization")
@Tag(name = "Airports", description = "Get, modify or create airports")
@RequestMapping("/airport")
@RestController
public class AirportController {

    private final IAirportService airportService;
    @Autowired
    public AirportController(IAirportService airportService){
        this.airportService = airportService;
    }

    @Operation(description = "Get all airports in db.")
    @GetMapping
    public List<Airport> getAllAirports(){
        return airportService.getAllAirports();
    }

    @Operation(description = "Get an airport from airport id.")
    @GetMapping(value = "/{id}")
    public Airport getAirport(@PathVariable int id){
        return airportService.getAirport(id);
    }

    @Operation(description = "Update an airport which id is given. Filled fields will have updated.")
    @PutMapping
    public Airport updateAirport(@RequestBody UpdateAirportRequest updateAirportRequestObject){
        return airportService.updateAirport(updateAirportRequestObject);
    }

    @Operation(description = "Create an airport.")
    @PostMapping
    public Airport createAirport(@RequestBody CreateAirportRequest createAirportRequestObject){
        return airportService.createAirport(createAirportRequestObject);
    }
}
