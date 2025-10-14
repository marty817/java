
package com.example.busapp.controller;

import com.example.busapp.dto.AuthDTOs.Receipt;
import com.example.busapp.model.Trip;
import com.example.busapp.model.User;
import com.example.busapp.service.TripService;
import com.example.busapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TripController {
    private final TripService tripService;
    private final UserService userService;

    public TripController(TripService tripService, UserService userService) {
        this.tripService = tripService;
        this.userService = userService;
    }

    @GetMapping("/trips")
    public ResponseEntity<?> list(@RequestParam(required = false) String origin,
                                  @RequestParam(required = false) String destination,
                                  @RequestParam(required = false) String from) {
        LocalDateTime dt = null;
        if (from != null) dt = LocalDateTime.parse(from);
        List<Trip> trips = tripService.list(origin, destination, dt);
        var out = trips.stream().map(t -> Map.of(
                "id", t.getId(),
                "origin", t.getOrigin(),
                "destination", t.getDestination(),
                "departureTime", t.getDepartureTime(),
                "price", t.getPrice()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @PostMapping("/trips")
    public ResponseEntity<?> createTrip(@RequestBody Trip t, Authentication auth) {
        // only admin
        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).body(Map.of("error","admin only"));
        }
        if (t.getOrigin()==null||t.getDestination()==null||t.getDepartureTime()==null||t.getPrice()==null) {
            return ResponseEntity.badRequest().body(Map.of("error","missing fields"));
        }
        Trip saved = tripService.create(t);
        return ResponseEntity.created(java.net.URI.create("/trips/"+saved.getId())).body(Map.of("id", saved.getId()));
    }

    @PostMapping("/trips/{tripId}/buy")
    public ResponseEntity<?> buy(@PathVariable Integer tripId, @RequestBody Map<String, Object> body, Authentication auth) {
        // auth required
        String email = (String) auth.getPrincipal();
        var uopt = userService.findByEmail(email);
        if (uopt.isEmpty()) return ResponseEntity.status(401).build();
        User u = uopt.get();
        var toOpt = tripService.findById(tripId);
        if (toOpt.isEmpty()) return ResponseEntity.notFound().build();
        Trip trip = toOpt.get();
        try {
            // transactional charge
            User charged = userService.chargeIfEnough(u.getId(), trip.getPrice());
            Receipt r = new Receipt(u.getId(), trip.getId(), trip.getPrice(), charged.getCredit());
            return ResponseEntity.ok(r);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(422).body(Map.of("error","Credito insufficiente"));
        }
    }
}
