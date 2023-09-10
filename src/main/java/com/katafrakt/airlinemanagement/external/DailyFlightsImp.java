package com.katafrakt.airlinemanagement.external;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.List;

public class DailyFlightsImp implements DailyFlights{

    @Value("${flightSchedule.dailySaveFlights.url}")
    private String url;
    @Override
    public List<CreateFlightRequest> getFlights(OffsetDateTime date) throws URISyntaxException, IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(response.body(), new TypeReference<>() {});
    }
}
