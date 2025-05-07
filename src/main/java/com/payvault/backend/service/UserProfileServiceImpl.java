package com.payvault.backend.service;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.entity.Address;
import com.payvault.backend.entity.UserPreference;
import com.payvault.backend.entity.UserProfile;
import com.payvault.backend.exception.ResourceNotFoundException;
import com.payvault.backend.repository.AddressRepository;
import com.payvault.backend.repository.PreferenceRepository;
import com.payvault.backend.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;
    private final PreferenceRepository preferenceRepository;

    @Override
    public UserProfileResponse createUser(UserProfileRequest userRequest) {

        String encryptedPassword = new BCryptPasswordEncoder(12).encode(userRequest.getPassword());

        UserProfile user = UserProfile.builder()
                .username(userRequest.getUsername())
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(encryptedPassword)
                .phoneNumber(userRequest.getPhoneNumber())
                .bio(userRequest.getBio())
                .profilePictureUrl(userRequest.getProfilePictureUrl())
                .role(userRequest.getRole())
                .build();

        UserProfile savedUser = userProfileRepository.save(user);
        return mapToUserProfileResponse(savedUser);
    }


    @Override
    public UserProfileResponse getUserById(UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        return mapToUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getUserByUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return mapToUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getUserByEmail(String email) {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return mapToUserProfileResponse(userProfile);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userProfileRepository.existsByEmail(email);

    }


    @Override
    public boolean existsByUsername(String username) {
        return userProfileRepository.existsByUsername(username);
    }

    @Override
    public Address createAddress(Address address, UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        address.setUserProfile(userProfile);
        return addressRepository.save(address); // Ensure addressRepository is injected
    }

    @Override
    public UserPreference createUserPreference(UserPreference userPreference, UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        userPreference.setUserProfile(userProfile);
        return preferenceRepository.save(userPreference);
    }

    private UserProfileResponse mapToUserProfileResponse(UserProfile savedUser) {

        return UserProfileResponse.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .bio(savedUser.getBio())
                .profilePictureUrl(savedUser.getProfilePictureUrl())
                .role(savedUser.getRole())
                .build();

    }
}
