
package com.example.busapp.repository;

import com.example.busapp.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(String origin, String destination);
    List<Trip> findByDepartureTimeAfter(LocalDateTime dt);
}
