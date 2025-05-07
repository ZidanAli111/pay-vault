package com.payvault.backend.controller;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.entity.Address;
import com.payvault.backend.entity.UserPreference;
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

    @PostMapping("/address/{userId}")
    public ResponseEntity<Address> createAddress(@PathVariable UUID userId,@RequestBody Address address) {
        Address addressResponse = userProfileService.createAddress(address,userId);
        return ResponseEntity.ok(addressResponse);
    }

    @PostMapping("/preferences/{userId}")
    public ResponseEntity<UserPreference> createUserPreference(@PathVariable UUID userId,@RequestBody UserPreference userPreference) {
        UserPreference userPreferenceResponse = userProfileService.createUserPreference(userPreference,userId);
        return ResponseEntity.ok(userPreferenceResponse);
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
