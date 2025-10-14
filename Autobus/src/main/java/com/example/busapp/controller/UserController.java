
package com.example.busapp.controller;

import com.example.busapp.model.User;
import com.example.busapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        return userService.findById(id).map(u -> ResponseEntity.ok(Map.of(
                "id", u.getId(),
                "email", u.getEmail(),
                "credit", u.getCredit()
        ))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        String email = (String) auth.getPrincipal();
        var uopt = userService.findByEmail(email);
        if (uopt.isEmpty()) return ResponseEntity.status(401).build();
        var u = uopt.get();
        return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail(), "credit", u.getCredit(), "role", u.getRole()));
    }

    @PatchMapping("/users/{id}/credit/toup")
    public ResponseEntity<?> addCreditToUser(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        // used in phase1 - admin can top up any user if they call endpoint (no auth here other than global)
        try {
            BigDecimal amount = new BigDecimal(body.get("amount").toString());
            User u = userService.addCredit(id, amount);
            return ResponseEntity.ok(Map.of("id", u.getId(), "credit", u.getCredit()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PatchMapping("/me/credit/toup")
    public ResponseEntity<?> addCreditToMe(Authentication auth, @RequestBody Map<String, Object> body) {
        String email = (String) auth.getPrincipal();
        var uopt = userService.findByEmail(email);
        if (uopt.isEmpty()) return ResponseEntity.status(401).build();
        try {
            BigDecimal amount = new BigDecimal(body.get("amount").toString());
            User u = userService.addCredit(uopt.get().getId(), amount);
            return ResponseEntity.ok(Map.of("id", u.getId(), "credit", u.getCredit()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
