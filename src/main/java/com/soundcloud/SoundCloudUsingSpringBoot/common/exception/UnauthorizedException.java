package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;

import com.soundcloud.SoundCloudUsingSpringBoot.common.enums.AuthFailureReason;


/**
 * Thrown when authentication fails or is required.
 * 
 * Examples:
 * - JWT token is missing
 * - JWT token is expired
 * - JWT token is invalid
 * - Invalid credentials
 * - Authentication is required
*/
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(AuthFailureReason reason) {
        super(reason.getMessage());
    }

    

}
