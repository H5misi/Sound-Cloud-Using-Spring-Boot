package com.soundcloud.SoundCloudUsingSpringBoot.user.mapper;

import org.springframework.stereotype.Component;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.RegisterRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserSummaryResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.SuspensionStatus;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.VerificationStatus;

/**
 * Maps between User entities and User-related DTOs.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity into a UserSummaryResponse.
     *
     * @param user the user entity
     * @return a summary representation of the user
     */
    public UserSummaryResponse toSummaryResponse(User user) {

        return new UserSummaryResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName());
    }

    /**
     * Converts a registration request into a new User entity.
     *
     * @param request the registration request
     * @param encodedPassword the already encoded password
     * @return a new user entity initialized with default values
     */
    public User toEntity(RegisterRequest request, String encodedPassword) {

        return User
                .builder()
                .email(request.email())
                .username(request.username())
                .password(encodedPassword)
                .displayName(request.username())
                .accountType(request.accountType())

                .profileVisibility(ProfileVisibility.PUBLIC)
                .verificationStatus(VerificationStatus.PENDING)
                .suspensionStatus(SuspensionStatus.ACTIVE)
                .build();

    }
}
