package com.katafrakt.airlinemanagement.schedules;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IFlightSchedule {
    void dailySaveFlights() throws URISyntaxException, IOException, InterruptedException;
}
