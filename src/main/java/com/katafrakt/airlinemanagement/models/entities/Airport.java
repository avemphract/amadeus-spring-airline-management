package com.katafrakt.airlinemanagement.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SequenceGenerator(name="AIRPORT_ID_SEQ",sequenceName="AIRPORT_ID_SEQ", allocationSize=1)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Airport {
    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AIRPORT_ID_SEQ")
    @Column(nullable = false)
    private int id;

    @Getter @Setter
    @Column(nullable = false)
    private String name;

    @Getter @Setter
    @Column(nullable = false)
    private String city;
}
