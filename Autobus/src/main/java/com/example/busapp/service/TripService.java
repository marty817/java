
package com.example.busapp.service;

import com.example.busapp.model.Trip;
import com.example.busapp.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) { this.tripRepository = tripRepository; }

    public Trip create(Trip t) { return tripRepository.save(t); }

    public Optional<Trip> findById(Integer id) { return tripRepository.findById(id); }

    public List<Trip> list(String origin, String destination, LocalDateTime from) {
        if ((origin==null || origin.isBlank()) && (destination==null || destination.isBlank())) {
            if (from != null) return tripRepository.findByDepartureTimeAfter(from);
            return tripRepository.findAll();
        }
        // simple search
        return tripRepository.findByOriginContainingIgnoreCaseAndDestinationContainingIgnoreCase(
                origin==null ? "" : origin,
                destination==null ? "" : destination
        );
    }
}
