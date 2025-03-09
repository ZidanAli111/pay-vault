package com.payvault.backend.auth.controller;

import com.payvault.backend.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {

        authService.registerUser(username, password, response);

        return ResponseEntity.ok("User register Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {

        authService.authenticateUser(username, password, response);

        return ResponseEntity.ok("User Logged in Successfully");
    }


}
