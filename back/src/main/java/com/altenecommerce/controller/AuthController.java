package com.altenecommerce.controller;

import com.altenecommerce.dto.AuthRequest;
import com.altenecommerce.dto.AuthResponse;
import com.altenecommerce.dto.RegisterRequest;
import com.altenecommerce.entity.User;
import com.altenecommerce.service.AuthService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/account")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.register(
                request.getUsername(),
                request.getFirstname(),
                request.getEmail(),
                request.getPassword()
            );
            return ResponseEntity.ok(Map.of(
                "message", "Compte créé avec succès",
                "firstname", user.getFirstname()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", e.getMessage()
            ));
        }
    }
    @PostMapping("/token")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}

