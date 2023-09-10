package com.katafrakt.airlinemanagement.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
@Schema(name = "Flight Model Documentation", description = "Model")
@SequenceGenerator(name="FLIGHT_ID_SEQ",sequenceName="FLIGHT_ID_SEQ", allocationSize=1)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {


    @Schema(name = "Primary key of flight.", requiredMode = Schema.RequiredMode.NOT_REQUIRED, hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FLIGHT_ID_SEQ")
    @Getter @Setter
    private int id;

    @Schema(name = "Departure airport of flight", requiredMode = Schema.RequiredMode.REQUIRED)
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.DETACH)
    private Airport departureAirport;

    @Schema(name = "Destination airport of flight", requiredMode = Schema.RequiredMode.REQUIRED)
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Airport destinationAirport;

    @Getter @Setter
    @Column(nullable = false)
    private OffsetDateTime beginDate;

    @Getter @Setter
    @Column(nullable = false)
    private OffsetDateTime endDate;

    @Getter @Setter
    @Column(nullable = false)
    private double price;
}
