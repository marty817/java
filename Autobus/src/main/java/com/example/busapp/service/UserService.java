
package com.example.busapp.service;

import com.example.busapp.model.User;
import com.example.busapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String rawPassword, BigDecimal initialCredit, boolean asAdmin) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        String hash = passwordEncoder.encode(rawPassword);
        User u = new User(email, hash, asAdmin ? "ROLE_ADMIN" : "ROLE_USER", initialCredit);
        return userRepository.save(u);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public synchronized User addCredit(Integer userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        u.setCredit(u.getCredit().add(amount));
        return userRepository.save(u);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public synchronized User chargeIfEnough(Integer userId, java.math.BigDecimal amount) {
        User u = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (u.getCredit().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient credit");
        }
        u.setCredit(u.getCredit().subtract(amount));
        return userRepository.save(u);
    }
}
