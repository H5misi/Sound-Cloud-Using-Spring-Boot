package com.soundcloud.SoundCloudUsingSpringBoot.user.entity;


/**
 * Represents the visibility of a user's profile.
 *
 * Profiles can be:
 * - PUBLIC: visible to all users.
 * - PRIVATE: visible only according to the application's privacy rules.
 */
public enum ProfileVisibility {
    PUBLIC,
    PRIVATE
}
