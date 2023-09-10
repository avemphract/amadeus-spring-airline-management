package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.entities.Flight;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.models.requests.flight.UpdateFlightRequest;
import com.katafrakt.airlinemanagement.models.responses.flight.FlightSearchResponse;
import com.katafrakt.airlinemanagement.repositories.IAirportRepository;
import com.katafrakt.airlinemanagement.repositories.IFlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class FlightService {
    @Value("${flightService.maxTransferCount}") @Setter
    private Integer maxTransferCount = 2;
    private final IFlightRepository flightRepository;
    private final IAirportRepository airportRepository;

    public Flight getFlightById(int id){
        return flightRepository.getReferenceById(id);
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    public Flight updateFlight(UpdateFlightRequest updateFlightRequest) throws ResponseStatusException {
        Optional<Flight> optflight = flightRepository.findById(updateFlightRequest.getId());
        if (optflight.isPresent()){
            Flight flight = optflight.get();
            if (updateFlightRequest.getDepartureAirportId() != null)
                flight.setDepartureAirport(airportRepository.getReferenceById(updateFlightRequest.getId()));
            if (updateFlightRequest.getDestinationAirportId() != null)
                flight.setDestinationAirport(airportRepository.getReferenceById(updateFlightRequest.getId()));
            if (updateFlightRequest.getBeginDate() != null)
                flight.setBeginDate(updateFlightRequest.getBeginDate());
            if (updateFlightRequest.getEndDate() != null)
                flight.setEndDate(updateFlightRequest.getEndDate());
            if (updateFlightRequest.getPrice() != null)
                flight.setPrice(updateFlightRequest.getPrice());
            flightRepository.saveAndFlush(flight);
            return flight;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Flight createFlight(CreateFlightRequest createFlightRequest){
        Flight flight =
                new Flight(
                        0,
                        airportRepository.getReferenceById(createFlightRequest.getDepartureAirportId()),
                        airportRepository.getReferenceById(createFlightRequest.getDestinationAirportId()),
                        createFlightRequest.getBeginDate(),
                        createFlightRequest.getEndDate(),
                        createFlightRequest.getPrice());
        flightRepository.saveAndFlush(flight);
        return flight;
    }

    public FlightSearchResponse search(String departureCity, String destinationCity, String beginDate, String returnDate) {
        List<FlightSearchResponse.FlightOption> departureFlights = new ArrayList<>();
        search(departureCity, destinationCity, OffsetDateTime.parse(beginDate+"T00:00:00.000+00:00"), departureFlights);
        Collections.sort(departureFlights);
        List<FlightSearchResponse.FlightOption> returnFlights = null;
        if (returnDate != null){
            returnFlights = new ArrayList<>();
            search(destinationCity, departureCity, OffsetDateTime.parse(returnDate+"T00:00:00.000+00:00"), returnFlights);
            Collections.sort(returnFlights);
        }

        return new FlightSearchResponse(departureCity, destinationCity, departureFlights, returnFlights);
    }

    private void search(String destinationCity, List<FlightSearchResponse.FlightOption> flightOptions, List<Flight> flights, int transferCount){
        for(Flight f:flightRepository.getFlights(flights.get(flights.size()-1).getDestinationAirport().getCity(), flights.get(flights.size()-1).getEndDate(), flights.get(flights.size()-1).getEndDate().plusDays(1))){
            if(f.getDestinationAirport().getCity().equals(destinationCity)){
                flightOptions.add(new FlightSearchResponse.FlightOption(flights,f));
            }
            else if (transferCount + 1 < maxTransferCount){
                var nFlights = new ArrayList<Flight>(flights);
                nFlights.add(f);
                search(destinationCity, flightOptions, nFlights, transferCount + 1);
            }
            else {
                break;
            }
        }
    }
    private void search(String departureCity, String destinationCity, OffsetDateTime beginDate, List<FlightSearchResponse.FlightOption> flightOptions){
        for(Flight f:flightRepository.getFlights(departureCity, beginDate, beginDate.plusDays(1))){
            if(f.getDestinationAirport().getCity().equals(destinationCity)){
                flightOptions.add(new FlightSearchResponse.FlightOption(f));
            }
            else if (1 < maxTransferCount){
                var nFlights = new ArrayList<Flight>();
                nFlights.add(f);
                search(destinationCity, flightOptions, nFlights, 1);
            }
            else {
                break;
            }
        }
    }
}
