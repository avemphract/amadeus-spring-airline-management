package com.katafrakt.airlinemanagement.models.responses.flight;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import com.katafrakt.airlinemanagement.models.entities.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchResponse {
    @Getter @Setter
    private String departureCityId;

    @Getter @Setter
    private String destinationCityId;

    @Getter @Setter
    private List<FlightOption> departureFlightOptions;

    @Getter @Setter
    private List<FlightOption> returnFlightOptions;

    @NoArgsConstructor
    public static class FlightOption implements Comparable<FlightOption> {

        @Getter @Setter
        private List<Flight> flights;

        @Getter
        private double totalPrice;

        @Getter
        private Airport departureAirport;

        @Getter
        private Airport destinationAirport;

        public FlightOption(List<Flight> flights, Flight flight){
            this.flights = new ArrayList<>(flights);
            this.flights.add(flight);
            this.totalPrice = this.flights.stream().mapToDouble(Flight::getPrice).sum();
            this.departureAirport = this.flights.get(0).getDepartureAirport();
            this.destinationAirport = this.flights.get(this.flights.size() - 1).getDestinationAirport();
        }
        public FlightOption(Flight flight){
            this.flights = new ArrayList<>();
            this.flights.add(flight);
            this.totalPrice = this.flights.stream().mapToDouble(Flight::getPrice).sum();
            this.departureAirport = this.flights.get(0).getDepartureAirport();
            this.destinationAirport = this.flights.get(this.flights.size() - 1).getDestinationAirport();
        }

        @Override
        public int compareTo(FlightOption o) {
            return Double.compare(getTotalPrice(), o.getTotalPrice());
        }
    }
}
