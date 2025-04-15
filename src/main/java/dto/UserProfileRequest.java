package dto;

import lombok.Data;

@Data
public class UserProfileRequest {
    private String username;
    private String bio;
    private String profilePictureUrl;
}
