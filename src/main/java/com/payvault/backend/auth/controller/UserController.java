package com.payvault.backend.auth.controller;

import com.payvault.backend.auth.dto.UserResponse;
import com.payvault.backend.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get current user details")
    public ResponseEntity<UserResponse> getCurrentUser() {
        // In a real implementation, you would get the current user from the security context
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/{userUuid}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userUuid) {
        return ResponseEntity.ok(userService.getUserById(userUuid));
    }
}