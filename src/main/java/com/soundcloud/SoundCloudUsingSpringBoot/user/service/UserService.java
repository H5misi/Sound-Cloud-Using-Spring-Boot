package com.soundcloud.SoundCloudUsingSpringBoot.user.service;

import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.ProfileResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UpdateProfileRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserResponse;

public interface UserService {

    UserResponse getMyProfile(String email);

    ProfileResponse getUserProfile(String username, String requesterEmail);

    UserResponse updateMyProfile(String email, UpdateProfileRequest request);
}
