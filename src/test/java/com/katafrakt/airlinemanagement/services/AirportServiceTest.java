package com.katafrakt.airlinemanagement.services;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.requests.airport.CreateAirportRequest;
import com.katafrakt.airlinemanagement.repositories.IAirportRepository;
import com.katafrakt.airlinemanagement.services.imp.AirportServiceImp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @InjectMocks
    private AirportServiceImp airportService;

    @Mock
    private IAirportRepository airportRepository;

    @Test
    public void createAirportNameMustBeExist(){
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest("","Istanbul")));
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest(" ","Istanbul")));
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest("   \n","Istanbul")));

        assertDoesNotThrow(()->airportService.createAirport(new CreateAirportRequest("Sabiha Gokcen","Istanbul")));
        assertDoesNotThrow(()->airportService.createAirport(new CreateAirportRequest("-","Istanbul")));

    }

    @Test
    public void createAirportCityNameMustBeExist(){
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest("","")));
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest("London Airport","")));
        assertThrows(RuntimeException.class, ()->airportService.createAirport(new CreateAirportRequest("New york Airport"," \n ")));

        assertDoesNotThrow(()->airportService.createAirport(new CreateAirportRequest("New york Airport","New york")));
        assertDoesNotThrow(()->airportService.createAirport(new CreateAirportRequest("New york Airport","-\n")));

    }
}
