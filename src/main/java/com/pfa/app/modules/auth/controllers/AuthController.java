package com.pfa.app.modules.auth.controllers;

import com.pfa.app.modules.auth.dto.AuthResponse;
import com.pfa.app.modules.auth.dto.LoginRequest;
import com.pfa.app.modules.auth.dto.RegisterRequest;
import com.pfa.app.modules.auth.services.AuthService;
import com.pfa.app.modules.user.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows React frontend local execution to communicate smoothly
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.authenticate(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> handleRegistration(@RequestBody RegisterRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            User registeredUser = authService.registerNewUser(request);

            responseBody.put("success", true);
            responseBody.put("message", "User account generated successfully.");
            responseBody.put("userId", registeredUser.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);

        } catch (IllegalArgumentException e) {
            responseBody.put("success", false);
            responseBody.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);

        } catch (Exception e) {
            responseBody.put("success", false);
            responseBody.put("message", "An unexpected core system server error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}