package com.soundcloud.SoundCloudUsingSpringBoot.user.mapper;

import org.springframework.stereotype.Component;

import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserSummaryResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

/**
 * Maps User entities to User-related DTOs.
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
}
