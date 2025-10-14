package com.example.bustrips.service;

import com.example.bustrips.entity.User;
import com.example.bustrips.dto.UserRegisterDto;
import com.example.bustrips.repository.UserRepository;
import com.example.bustrips.exception.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User register(UserRegisterDto dto) {
        User u = new User();
        u.setEmail(dto.email);
        u.setPassword(passwordEncoder.encode(dto.password));
        u.setCredit(dto.credit == null ? BigDecimal.ZERO : dto.credit);
        return userRepository.save(u);
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional
    public User topUp(Integer id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        User u = getById(id);
        u.setCredit(u.getCredit().add(amount));
        return userRepository.save(u);
    }
}
