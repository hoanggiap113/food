package com.food.controllers;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.AuthenticationResponseDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.services.JwtService;
import com.food.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO request) {
        long check = userService.saveUser(request);
        if (check != 0) return ResponseEntity.ok("User registered successfully");
        else return ResponseEntity.badRequest().body("Email already exist");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        UserDetailResponse user = userService.authenticate(request);
        String token = jwtService.generateToken(user.getEmail(), Collections.singletonList(user.getName()));
        return ResponseEntity.ok(new AuthenticationResponseDTO(token, true));
    }

}
