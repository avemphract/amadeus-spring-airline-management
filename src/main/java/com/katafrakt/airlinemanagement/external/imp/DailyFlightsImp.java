package com.katafrakt.airlinemanagement.external.imp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katafrakt.airlinemanagement.external.IDailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.OffsetDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(value = "application.isTest", havingValue = "false", matchIfMissing = false)
@RequiredArgsConstructor
public class DailyFlightsImp implements IDailyFlights {

    @Value("${flightSchedule.dailySaveFlights.path}") @Getter @Setter
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
