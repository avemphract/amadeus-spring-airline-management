package com.katafrakt.airlinemanagement.external.imp;

import com.katafrakt.airlinemanagement.external.IDailyFlights;
import com.katafrakt.airlinemanagement.models.requests.flight.CreateFlightRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@ConditionalOnProperty(value = "application.isTest", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class DailyFlightsImpMock implements IDailyFlights {
    @Value("${dailyFlightsMock.randomGeneratedFlights}") @Getter @Setter
    private Integer randomGeneratedFlights;
    private final Random rand = new Random();

    @Override
    public List<CreateFlightRequest> getFlights(OffsetDateTime date) {
        List<CreateFlightRequest> createFlightRequests = new ArrayList<>();
        for (int i=0;i<randomGeneratedFlights;i++){
            createFlightRequests.add(generateRandomRequest(date));
        }
        return createFlightRequests;
    }

    public CreateFlightRequest generateRandomRequest(OffsetDateTime date){
        int departureId = rand.nextInt(1,10);
        int destinationId = rand.nextInt(1,9);
        if (destinationId == departureId)
            destinationId++;
        OffsetDateTime departureDate = date.plusMinutes(rand.nextInt(3600));
        OffsetDateTime destinationDate = departureDate.plusMinutes(rand.nextInt(0,1800));
        return new CreateFlightRequest(departureId, destinationId, departureDate, destinationDate, rand.nextInt(100,1000));
    }
}
