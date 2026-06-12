package com.soundcloud.SoundCloudUsingSpringBoot.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a standardized error response returned
 * to the client when an exception occurs.
 *
 * Examples:
 * {
 *     "status": 404,
 *     "message": "User with identifier [5] was not found!"
 * }
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;

}
