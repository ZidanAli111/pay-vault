package com.payvault.backend.utils;

import com.payvault.backend.dto.UserProfileResponse;
import com.payvault.backend.entity.UserProfile;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;


@Component
public class UserProfileHelper {

    public UserProfileResponse mapToUserProfileResponse(UserProfile savedUser) {

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

    public String mapCountryToUserPreference(String country) {
        if (country == null || country.isBlank()) return "USD";

        for (Locale locale : Locale.getAvailableLocales()) {
            if (locale.getDisplayCountry().equalsIgnoreCase(country.trim())) {
                try {
                    Currency currency = Currency.getInstance(locale);
                    return currency.getCurrencyCode().toUpperCase();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return "USD";
    }

}
