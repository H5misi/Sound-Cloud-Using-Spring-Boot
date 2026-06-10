package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;


/**
 * Thrown when a request contains invalid data or violates
 * business rules defined by the application.
 * 
 * Examples:
 * - Email already exists
 * - User follows himself
 * - Upload limit exceeded
 * - Invalid playlist name
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

    
}
