package com.soundcloud.SoundCloudUsingSpringBoot.user.dto;

/**
 * Lightweight representation of a user.
 *
 * Used whenever the application needs to identify or
 * display a user without exposing the complete profile.
 *
 * Typical use cases:
 * - Authentication responses
 * - Track ownership
 * - Playlist ownership
 * - Comments
 * - Search results
 */
public record UserSummaryResponse(

        /**
         * Unique identifier of the user.
         */
        Long id,

        /**
         * Unique username used in profile URLs and mentions.
         */
        String username,

        /**
         * Human-readable name displayed throughout the application.
         */
        String displayName

) {

}
