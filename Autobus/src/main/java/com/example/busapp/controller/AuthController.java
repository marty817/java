
package com.example.busapp.controller;

import com.example.busapp.dto.AuthDTOs.*;
import com.example.busapp.model.User;
import com.example.busapp.security.JwtUtil;
import com.example.busapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req, @RequestParam(defaultValue = "false") boolean admin) {
        if (req.email == null || req.password == null) return ResponseEntity.badRequest().body(Map.of("error","email and password required"));
        try {
            User u = userService.register(req.email, req.password, req.credit, admin);
            return ResponseEntity.created(URI.create("/users/"+u.getId())).body(Map.of("id", u.getId(), "email", u.getEmail()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(409).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.email == null || req.password == null) return ResponseEntity.badRequest().body(Map.of("error","email and password required"));
        var opt = userService.findByEmail(req.email);
        if (opt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error","invalid credentials"));
        User u = opt.get();
        if (!passwordEncoder.matches(req.password, u.getPasswordHash())) {
            return ResponseEntity.status(401).body(Map.of("error","invalid credentials"));
        }
        String token = jwtUtil.generateToken(u.getEmail(), u.getRole());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
