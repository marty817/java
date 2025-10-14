package com.example.bustrips.controller;

import com.example.bustrips.dto.CreditTopUpDto;
import com.example.bustrips.dto.UserRegisterDto;
import com.example.bustrips.entity.User;
import com.example.bustrips.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ POST /users/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody UserRegisterDto dto) {
        return userService.register(dto);
    }

    // ✅ GET /users/{id}
    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getById(id);
    }

    // ✅ PATCH /users/{id}/credit/topup
    @PatchMapping("/{id}/credit/topup")
    public User topUp(@PathVariable Integer id, @Valid @RequestBody CreditTopUpDto dto) {
        return userService.topUp(id, dto.amount);
    }
}