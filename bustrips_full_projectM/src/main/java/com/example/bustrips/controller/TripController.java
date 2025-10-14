package com.example.bustrips.controller;

import com.example.bustrips.dto.PurchaseReceiptDto;
import com.example.bustrips.entity.Trip;
import com.example.bustrips.service.TripService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    // ✅ GET /trips
    @GetMapping
    public List<Trip> listTrips() {
        return tripService.listAll();
    }

    // ✅ POST /trips/{tripId}/buy
    @PostMapping("/{tripId}/buy")
    public PurchaseReceiptDto buyTrip(@PathVariable Integer tripId, @RequestBody BuyRequest request) {
        return tripService.buyTrip(request.getUserId(), tripId);
    }

    // ✅ Classe interna per la richiesta di acquisto
    public static class BuyRequest {
        private Integer userId;
        public Integer getUserId() { return userId; }
        public void setUserId(Integer userId) { this.userId = userId; }
    }
}