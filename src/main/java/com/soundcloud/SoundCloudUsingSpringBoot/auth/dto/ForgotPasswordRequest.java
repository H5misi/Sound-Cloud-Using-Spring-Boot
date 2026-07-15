package com.soundcloud.SoundCloudUsingSpringBoot.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body used to initiate the password reset process.
 *
 * If the email belongs to an existing account,
 * the application sends a password reset link.
 */
public record ForgotPasswordRequest(

        /**
         * Registered email address.
         */
        @NotBlank @Email String email

) {

}
