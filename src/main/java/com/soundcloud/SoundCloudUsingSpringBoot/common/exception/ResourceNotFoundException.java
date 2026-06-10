package com.soundcloud.SoundCloudUsingSpringBoot.common.exception;



/**
 * Thrown when a requested resource cannot be found.
 * 
 * Examples:
 * - User does not exist
 * - Track does not exist
 * - Playlist does not exist
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Object identifier) {

        super(String.format("%s with identifier [%s] was not found!",
                resource,
                identifier));

    }

}
