package com.food.controllers;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.AuthenticationResponseDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.services.JwtService;
import com.food.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final IUserService userService;
    private final JwtService jwtService;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the application!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO request) {
        long check = userService.saveUser(request);
        if (check != 0) return ResponseEntity.ok("User registered successfully");
        else return ResponseEntity.badRequest().body("Email already exist");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            UserDetailResponse user = userService.authenticate(request);
            String token = jwtService.generateToken(user.getEmail(), Collections.singletonList(user.getRole()));
            return ResponseEntity.ok(new AuthenticationResponseDTO(token, true));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống khi đăng nhập");
        }
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginPage() {
        return ResponseEntity.ok("{\"message\": \"Vui lòng đăng nhập\", \"loginUrl\": \"/auth/login\", \"googleLoginUrl\": \"/oauth2/authorization/google\"}");
    }

    @GetMapping("/login-error")
    public ResponseEntity<?> loginError(@RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google login failed");
        }
        return ResponseEntity.ok("Please login");
    }

    @GetMapping("/google-callback")
    public ResponseEntity<?> googleCallback(@RequestParam("token") String token) {
        return ResponseEntity.ok(new AuthenticationResponseDTO(token, true));
    }
}
