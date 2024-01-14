package com.katafrakt.airlinemanagement.schedules.imp;

import com.katafrakt.airlinemanagement.external.IDailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.schedules.IFlightSchedule;
import com.katafrakt.airlinemanagement.services.IFlightService;
import com.katafrakt.airlinemanagement.services.imp.FlightServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

@ConditionalOnProperty(value = "application.isTest", havingValue = "true", matchIfMissing = true)
@EnableAsync
@Component
public class FlightScheduleImp implements IFlightSchedule {
    private final IFlightService flightService;
    private final IDailyFlights dailyFlights;

    @Autowired
    public FlightScheduleImp(IFlightService flightService, IDailyFlights dailyFlights) {
        this.flightService = flightService;
        this.dailyFlights = dailyFlights;
    }

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void dailySaveFlights() throws URISyntaxException, IOException, InterruptedException {
        for (CreateFlightRequest createFlightRequest:dailyFlights.getFlights(OffsetDateTime.now().plusDays(180))){
            flightService.createFlight(createFlightRequest);
        }
    }
}
