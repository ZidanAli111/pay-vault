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
    private final UserProfileHelper userProfileHelper;

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

        if (addressRepository.findByUserProfile(userProfile) != null) {
            throw new IllegalStateException("Address already exists for user with ID: " + userId);
        }

        address.setUserProfile(userProfile);
        country = address.getCountry();

        if (!preferenceRepository.existsByUserProfile(userProfile)) {
            UserPreference defaultPreference = UserPreference.builder()
                    .userProfile(userProfile)
                    .currency(userProfileHelper.mapCountryToUserPreference(country))
                    .darkMode(false)
                    .notificationSettings("EMAIL")
                    .build();
            preferenceRepository.save(defaultPreference);
        }

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address, UUID userId) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        Address existingAddress = addressRepository.findByUserProfile(userProfile);
        if (existingAddress == null) {
            throw new IllegalStateException("No address found for user with ID: " + userId);
        }

        existingAddress.setCity(address.getCity());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setPincode(address.getPincode());
        existingAddress.setState(address.getState());

        return addressRepository.save(existingAddress);
    }

    @Override
    public UserPreference updateUserPreference(UserPreference userPreference, UUID userId) {

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId));

        UserPreference existingPreference = preferenceRepository.findByUserProfile(userProfile);
        if (existingPreference == null) {
            throw new ResourceNotFoundException(USER_NOT_FOUND_WITH_ID + userId);
        }

        existingPreference.setCurrency(userPreference.getCurrency());
        existingPreference.setDarkMode(userPreference.isDarkMode());
        existingPreference.setNotificationSettings(userPreference.getNotificationSettings());
        existingPreference.setUserProfile(userProfile);

        return preferenceRepository.save(existingPreference);
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
