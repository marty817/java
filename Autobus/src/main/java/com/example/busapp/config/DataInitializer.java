
package com.example.busapp.config;

import com.example.busapp.model.Trip;
import com.example.busapp.service.TripService;
import com.example.busapp.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer {
    private final UserService userService;
    private final TripService tripService;

    public DataInitializer(UserService userService, TripService tripService) { this.userService = userService; this.tripService = tripService; }

    @PostConstruct
    public void init() {
        try {
            userService.register("admin@example.com", "adminpass", new BigDecimal("0"), true);
        } catch (Exception e) {}
        try {
            userService.register("user@example.com", "userpass", new BigDecimal("50.00"), false);
        } catch (Exception e) {}

        tripService.create(new Trip("Rome","Florence", LocalDateTime.now().plusDays(1), new BigDecimal("15.50")));
        tripService.create(new Trip("Milan","Venice", LocalDateTime.now().plusDays(2), new BigDecimal("22.00")));
    }
}
