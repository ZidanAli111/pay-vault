package com.payvault.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserProfileResponse {
    private UUID userId;
    private String email;
    private String username;
    private String password;
    private String bio;
    private String profilePictureUrl;
}
