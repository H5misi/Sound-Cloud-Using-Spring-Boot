package com.soundcloud.SoundCloudUsingSpringBoot.user.mapper;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.RegisterRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserProfileResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserResponse;
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


    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getBio(),
                user.getLocation(),
                user.getAccountType(),
                user.getProfileVisibility(),
                user.getProfileImageUrl(),
                user.getCoverImageUrl(),
                user.getInstagramUrl(),
                user.getTwitterUrl(),
                user.getWebsiteUrl(),
                // creates an unmodifiable copy of the genres so the response doesn't directly
                // expose the entity's mutable collection.
                Set.copyOf(user.getFavoriteGenres()));
    }

    /**
     * Ternary operator: 
     * 
     * showExtendedProfile ? user.getLocatio() : null
     * 
     * is shorthand to:
     * 
     * if (showExtendedProfile){
     *     return user.getLocation();
     * } else {
     *     return null;
     * }
     * 
     */
    public UserProfileResponse toUserProfileResponse(
            User user, boolean showExtendedProfile) {
        return new UserProfileResponse(
                user.getUsername(),
                user.getDisplayName(),
                user.getBio(),
                showExtendedProfile ? user.getLocation() : null,
                user.getAccountType(),
                user.getProfileVisibility(),
                user.getProfileImageUrl(),
                user.getCoverImageUrl(),
                showExtendedProfile ? user.getInstagramUrl() : null,
                showExtendedProfile ? user.getTwitterUrl() : null,
                showExtendedProfile ? user.getWebsiteUrl() : null,
                showExtendedProfile ? Set.copyOf(user.getFavoriteGenres()) : Set.of());
    }
}
