package com.payvault.backend.dto;

import com.payvault.backend.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserProfileResponse {
    private UUID userId;
    private String name;
    private String email;
    private String username;
    private String password;
    private String bio;
    private String profilePictureUrl;
    private String phoneNumber;
    private Role role;
}
