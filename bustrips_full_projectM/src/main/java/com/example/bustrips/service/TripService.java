package com.example.bustrips.service;

import com.example.bustrips.dto.PurchaseReceiptDto;
import com.example.bustrips.entity.Trip;
import com.example.bustrips.entity.User;
import com.example.bustrips.exception.NotFoundException;
import com.example.bustrips.repository.TripRepository;
import com.example.bustrips.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public TripService(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public List<Trip> listAll() { return tripRepository.findAll(); }

    @Transactional
    public PurchaseReceiptDto buyTrip(Integer userId, Integer tripId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new NotFoundException("Trip not found"));

        BigDecimal price = trip.getPrice();
        if (user.getCredit().compareTo(price) < 0)
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Credito insufficiente");

        user.setCredit(user.getCredit().subtract(price));
        userRepository.save(user);
        return new PurchaseReceiptDto(user.getId(), trip.getId(), price, user.getCredit());
    }
}
