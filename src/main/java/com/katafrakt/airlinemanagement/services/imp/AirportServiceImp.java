package com.katafrakt.airlinemanagement.services.imp;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.models.requests.airport.UpdateAirportRequest;
import com.katafrakt.airlinemanagement.repositories.IAirportRepository;
import com.katafrakt.airlinemanagement.services.IAirportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AirportServiceImp implements IAirportService {
    private static final Logger logger = LoggerFactory.getLogger(AirportServiceImp.class);


    private final IAirportRepository airportRepository;
    @Autowired
    public AirportServiceImp(IAirportRepository airportRepository){
        this.airportRepository = airportRepository;
    }


    @Override
    public List<Airport> getAllAirports(){
        try {
            return airportRepository.findAll();
        }
        catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }
    @Override
    public Airport getAirport(int id){
        try {
            return airportRepository.getReferenceById(id);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
        }
    }

    @Override
    public Airport updateAirport(UpdateAirportRequest updateAirportRequestObject) {
        Optional<Airport> opAirport = airportRepository.findById(updateAirportRequestObject.getId());
        if (opAirport.isPresent()){
            Airport airport = opAirport.get();
            if (updateAirportRequestObject.getName() != null)
                airport.setName(updateAirportRequestObject.getName());
            if (updateAirportRequestObject.getCity() != null)
                airport.setCity(updateAirportRequestObject.getCity());
            try {
                airportRepository.saveAndFlush(airport);
            }
            catch (Exception exception){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error");
            }
            return airport;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Airport id has not found in database");
        }
    }

    @Override
    public Airport createAirport(CreateAirportRequest createAirportRequestObject) {
        if(createAirportRequestObject.getCity().isBlank())
            throw new RuntimeException("City Name must be filled.");
        if(createAirportRequestObject.getName().isBlank())
            throw new RuntimeException("Airport Name must be filled.");
        Airport airport = new Airport(0, createAirportRequestObject.getName(), createAirportRequestObject.getCity());
        airportRepository.saveAndFlush(airport);
        return airport;
    }
}
