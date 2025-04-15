package controller;

import dto.UserProfileRequest;
import dto.UserProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.UserProfileService;

@RestController
@RequestMapping("/v1/profiles")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public UserProfileResponse getProfile(@PathVariable String userId) {
        return userProfileService.getProfile(userId);
    }

    @PutMapping("/{userId}")
    public UserProfileResponse updateProfile(@PathVariable String userId, @RequestBody UserProfileRequest request) {
        return userProfileService.updateProfile(userId, request);
    }

}
