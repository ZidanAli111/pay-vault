package com.payvault.backend.controller;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor
public class UserProfileController {


    private final UserProfileService userProfileService;

    @PostMapping("/create")
    public ResponseEntity<UserProfileResponse> createUser(@RequestBody UserProfileRequest userRequest) {
        UserProfileResponse userResponse = userProfileService.createUser(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable UUID userId) {
        UserProfileResponse userResponse = userProfileService.getUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserProfileResponse> getUserByUsername(@PathVariable String username) {
        UserProfileResponse user = userProfileService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfileResponse> getUserByEmail(@PathVariable String email) {
        UserProfileResponse user = userProfileService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }


}
