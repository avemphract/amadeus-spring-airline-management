package com.katafrakt.airlinemanagement;

import com.katafrakt.airlinemanagement.external.DailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.services.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class InitializationEvent implements ApplicationListener<ContextRefreshedEvent> {
    private final DailyFlights dailyFlights;
    private final FlightService flightService;

    @Value("${initializationEvent.dayCount}") @Setter
    private Integer dayCount;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (int i = 0;i<dayCount;i++){
            try {
                for (CreateFlightRequest createFlightRequest:dailyFlights.getFlights(OffsetDateTime.now().plusDays(i))){
                    flightService.createFlight(createFlightRequest);
                }
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
