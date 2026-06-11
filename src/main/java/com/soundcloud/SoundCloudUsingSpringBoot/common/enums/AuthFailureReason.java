package com.soundcloud.SoundCloudUsingSpringBoot.common.enums;

/**
 * Represents the possible authentication failure reasons.
 *
 * Used by UnauthorizedException to provide
 * consistent authentication error messages.
 */
public enum AuthFailureReason {
    TOKEN_MISSING("JWT token is missing"),
    TOKEN_EXPIRED("JWT token has expired"),
    TOKEN_INVALID("JWT token is invalid"),
    INVALID_CREDENTIALS("Invalid email or password"),
    AUTHENTICATION_REQUIRED("Authentication is required");

    private final String message;

    private AuthFailureReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

        

}
