package com.payvault.backend.service;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;

import java.util.UUID;

public interface UserProfileService {
    UserProfileResponse createUser(UserProfileRequest userRequest);

    UserProfileResponse getUserById(UUID userId);

    UserProfileResponse getUserByUsername(String username);

    UserProfileResponse getUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
