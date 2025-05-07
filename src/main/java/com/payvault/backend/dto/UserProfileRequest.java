package com.payvault.backend.dto;

import com.payvault.backend.entity.Role;
import lombok.Data;

@Data
public class UserProfileRequest {
    private String username;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String bio;
    private String profilePictureUrl;
    private Role role;

}
