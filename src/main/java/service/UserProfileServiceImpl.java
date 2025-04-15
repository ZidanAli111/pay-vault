package service;

import dto.UserProfileRequest;
import dto.UserProfileResponse;
import entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserProfileRepository;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileResponse getProfile(String userId) {
        UserProfile userProfile = userProfileRepository.
                findById(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponse(userProfile);
    }


    @Override
    public UserProfileResponse updateProfile(String userId, UserProfileRequest request) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponse(userProfile);
    }

    private UserProfileResponse mapToResponse(UserProfile userProfile) {
        return UserProfileResponse.builder()
                .userId(userProfile.getUserId())
                .username(userProfile.getUsername())
                .bio(userProfile.getBio())
                .profilePictureUrl(userProfile.getProfilePictureUrl())
                .build();
    }
}
