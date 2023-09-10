package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.models.requests.airport.UpdateAirportRequest;
import com.katafrakt.airlinemanagement.repositories.IAirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class AirportService {

    private final IAirportRepository airportRepository;
    @Autowired
    public AirportService(IAirportRepository airportRepository){
        this.airportRepository = airportRepository;
    }


    public List<Airport> getAllAirports(){
        return airportRepository.findAll();
    }
    public Airport getAirport(int id){
        return airportRepository.getReferenceById(id);
    }

    public Airport updateAirport(UpdateAirportRequest updateAirportRequestObject) {
        Optional<Airport> opAirport = airportRepository.findById(updateAirportRequestObject.getId());
        if (opAirport.isPresent()){
            Airport airport = opAirport.get();
            if (updateAirportRequestObject.getName() != null)
                airport.setName(updateAirportRequestObject.getName());
            if (updateAirportRequestObject.getCity() != null)
                airport.setCity(updateAirportRequestObject.getCity());
            airportRepository.saveAndFlush(airport);
            return airport;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Airport createAirport(CreateAirportRequest createAirportRequestObject) {
        Airport airport = new Airport(0, createAirportRequestObject.getName(), createAirportRequestObject.getCity());
        airportRepository.saveAndFlush(airport);
        return airport;
    }
}
