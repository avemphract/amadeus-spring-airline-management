package com.katafrakt.airlinemanagement.repositories;

import com.katafrakt.airlinemanagement.models.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface IFlightRepository  extends JpaRepository<Flight,Integer>{
    @Query(value = "SELECT F.* FROM FLIGHT F INNER JOIN AIRPORT A ON A.ID = F.DEPARTURE_AIRPORT_ID WHERE A.CITY = :city AND :minDate < F.BEGIN_DATE AND F.END_DATE < :maxDate",nativeQuery = true)
    List<Flight> getFlights(@Param("city") String city, @Param("minDate") OffsetDateTime minDate, @Param("maxDate") OffsetDateTime maxDate);
}
