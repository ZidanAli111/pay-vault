package service;

import dto.UserProfileRequest;
import dto.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getProfile(String userId);
    UserProfileResponse updateProfile(String userId, UserProfileRequest request);

}
