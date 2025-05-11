package com.payvault.backend.service;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.entity.Address;
import com.payvault.backend.entity.UserPreference;

import java.util.UUID;

public interface UserProfileService {
    UserProfileResponse createUser(UserProfileRequest userRequest);

    UserProfileResponse getUserById(UUID userId);

    UserProfileResponse getUserByUsername(String username);

    UserProfileResponse getUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Address createAddress(Address address,UUID userId);

    Address updateAddress(Address address,UUID userId);

    UserPreference updateUserPreference(UserPreference userPreference, UUID userId);
}
