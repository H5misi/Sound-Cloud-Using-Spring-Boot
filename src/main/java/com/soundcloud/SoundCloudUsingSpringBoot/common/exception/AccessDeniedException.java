package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;

/**
 * Thrown when an authenticated user attempts to access
 * a resource or perform an action without sufficient permissions.
 *
 * Examples:
 * - User tries to delete another user's track
 * - User tries to modify another user's playlist
 * - User tries to access the admin dashboard
 * - User tries to use a premium-only feature
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String message) {
        super(message);
    }

}
