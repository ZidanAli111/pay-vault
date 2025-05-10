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
import com.payvault.backend.utils.UserProfileHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.payvault.backend.constants.UserProfileConstants.USER_NOT_FOUND_WITH_ID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {


    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;
    private final PreferenceRepository preferenceRepository;
    private UserProfileHelper userProfileHelper;

    private String country;

    @Override
    public UserProfileResponse createUser(UserProfileRequest userRequest) {

        String encryptedPassword = new BCryptPasswordEncoder(12).encode(userRequest.getPassword());

        UserProfile user = UserProfile.builder()
                .name(userRequest.getName())
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(encryptedPassword)
                .phoneNumber(userRequest.getPhoneNumber())
                .bio(userRequest.getBio())
                .profilePictureUrl(userRequest.getProfilePictureUrl())
                .role(userRequest.getRole())
                .build();

        UserProfile savedUser = userProfileRepository.save(user);
        return userProfileHelper.mapToUserProfileResponse(savedUser);
    }

    @Override
    public Address createAddress(Address address, UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        address.setUserProfile(userProfile);

        country = address.getCountry();

        return addressRepository.save(address);
    }

    @Override
    public UserPreference createUserPreference(UserPreference userPreference, UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        String currency = userProfileHelper.mapCountryToUserPreference(country);

        userPreference.setUserProfile(userProfile);
        userPreference.setCurrency(currency);
        userPreference.setDarkMode(false);
        userPreference.setNotificationSettings("EMAIL");

        return preferenceRepository.save(userPreference);
    }

    @Override
    public UserProfileResponse getUserById(UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));
        return userProfileHelper.mapToUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getUserByUsername(String username) {
        UserProfile userProfile = userProfileRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return userProfileHelper.mapToUserProfileResponse(userProfile);
    }

    @Override
    public UserProfileResponse getUserByEmail(String email) {
        UserProfile userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userProfileHelper.mapToUserProfileResponse(userProfile);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userProfileRepository.existsByEmail(email);

    }


    @Override
    public boolean existsByUsername(String username) {
        return userProfileRepository.existsByUsername(username);
    }


}
