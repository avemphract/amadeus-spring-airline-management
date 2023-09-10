package com.katafrakt.airlinemanagement.schedules;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katafrakt.airlinemanagement.external.DailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import com.katafrakt.airlinemanagement.services.FlightService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.List;

@EnableAsync
@Component
@RequiredArgsConstructor
public class FlightSchedule {
    private final FlightService flightService;

    private final DailyFlights dailyFlights;

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void dailySaveFlights() throws URISyntaxException, IOException, InterruptedException {
        for (CreateFlightRequest createFlightRequest:dailyFlights.getFlights(OffsetDateTime.now().plusDays(180))){
            flightService.createFlight(createFlightRequest);
        }
    }
}
