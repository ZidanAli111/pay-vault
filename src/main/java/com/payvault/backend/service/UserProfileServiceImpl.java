package com.payvault.backend.service;

import com.payvault.backend.dto.UserProfileRequest;
import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.entity.UserProfile;
import com.payvault.backend.exception.ResourceNotFoundException;
import com.payvault.backend.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserProfileResponse createUser(UserProfileRequest userRequest) {
        UserProfile user = UserProfile.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .bio(userRequest.getBio())
                .profilePictureUrl(userRequest.getProfilePictureUrl())
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

    private UserProfileResponse mapToUserProfileResponse(UserProfile savedUser) {

        return UserProfileResponse.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .password(savedUser.getPassword())
                .bio(savedUser.getBio())
                .profilePictureUrl(savedUser.getProfilePictureUrl())
                .build();

    }
}
