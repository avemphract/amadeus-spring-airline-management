package com.katafrakt.airlinemanagement.repositories;

import com.katafrakt.airlinemanagement.models.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAirportRepository extends JpaRepository<Airport,Integer> {

}
