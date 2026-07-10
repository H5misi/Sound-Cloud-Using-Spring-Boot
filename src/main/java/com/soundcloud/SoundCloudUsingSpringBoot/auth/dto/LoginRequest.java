package com.soundcloud.SoundCloudUsingSpringBoot.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body used to authenticate an existing user.
 *
 * Contains the user's credentials required for login.
 */
public record LoginRequest(
        
        /**
         * Registered email address.
         *
         * @NotBlank: ensures an email is provided.
         * @Email: validates the email format.
         */
        @NotBlank
        @Email
        String email,


        /**
         * Raw password supplied during login.
         *
         * Only presence is validated here. Password strength
         * rules are enforced during registration (in RegisterRequest record).
         */
        @NotBlank
        String password) {

}
