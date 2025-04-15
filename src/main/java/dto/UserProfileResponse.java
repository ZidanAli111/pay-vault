package dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String userId;
    private String username;
    private String bio;
    private String profilePictureUrl;
}
