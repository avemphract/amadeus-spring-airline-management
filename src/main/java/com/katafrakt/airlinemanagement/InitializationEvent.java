package com.katafrakt.airlinemanagement;

import com.katafrakt.airlinemanagement.configurations.aop.DurationLoggable;
import com.katafrakt.airlinemanagement.external.IDailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.services.imp.FlightServiceImp;
import lombok.Getter;
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

    @Getter
    private static boolean initializationCompleted = false;
    private final IDailyFlights dailyFlights;
    private final FlightServiceImp flightService;
    @Value("${initializationEvent.dayCount}") @Getter @Setter
    private int dayCount;


    @DurationLoggable
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
        initializationCompleted = true;
    }
}
