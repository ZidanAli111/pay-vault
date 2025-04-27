package com.payvault.backend.dto;

import lombok.Data;

@Data
public class UserProfileRequest {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String profilePictureUrl;
}
