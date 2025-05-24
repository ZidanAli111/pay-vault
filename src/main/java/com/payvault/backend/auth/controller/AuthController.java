package com.payvault.backend.auth.controller;


import com.payvault.backend.auth.dto.AuthResponse;
import com.payvault.backend.auth.dto.LoginRequest;
import com.payvault.backend.auth.dto.RegisterRequest;
import com.payvault.backend.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "User authenticated successfully")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        AuthResponse authResponse = authService.authenticate(request);

        Cookie cookie = new Cookie("token", authResponse.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);

        response.addCookie(cookie);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/logout")
    @ApiResponse(responseCode = "200", description = "User logout successfully")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successful");
    }

    @DeleteMapping("/user/{username}")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        authService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/validate")
    @ApiResponse(responseCode = "200", description = "token validated successfully")
    public ResponseEntity<Boolean> validateToken(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(authService.validateToken(body.get("token")));
    }

}