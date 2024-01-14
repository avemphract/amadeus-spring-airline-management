package com.katafrakt.airlinemanagement.external;

import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;

public interface IDailyFlights {
    List<CreateFlightRequest> getFlights(OffsetDateTime date) throws URISyntaxException, IOException, InterruptedException;
}
