package com.soundcloud.SoundCloudUsingSpringBoot.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request body used to register a new user account.
 * 
 * Record: A special kind of class whose only job is to hold immutable data.
 * 
 * Java compiler automatically generates:
 * - Private final fields.
 * - Canonical constructor (or a custom constructor if provided)
 * - Accessors (~.email() instead of ~.getEmail() by Lombok)
 * - Equals(), hashCode(), toString()
 * 
 * Records can also define:
 * - custom methods
 * - compact constructors
 *
 * Contains the minimum information required to create
 * a new account.
 *
 * The display name is initialized with the username
 * and can be customized later by the user.
 */
public record RegisterRequest(

        /**
         * Unique username
         * 
         * @NotBlank: ensures the username is not null, empty, or whitespace.
         * @Size(min = , max = ): length of the username
         * @Pattern(regexp = "^[a-zA-Z0-9._]+$"):
         *                 ^ -> start of the string
         *                 [] -> any single character must match one of the
         *                 expressions
         *                 a-z -> lowercase letters
         *                 A-Z -> uppercase letters
         *                 0-9 -> numerical digits
         *                 ._ -> allowed special characters is . & _ only
         *                 + -> one or more
         *                 $ -> end of the string
         */
        @NotBlank
        @Size(min = 3, max = 30)
        @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username must contain only letters, numbers, dots, and underscores.")
        String username,

        /**
         * User's email address.
         *
         * @NotBlank: ensures an email is provided.
         * @Email: validates that the value follows a valid email address format.
         */
        @NotBlank
        @Email
        String email,

        /**
         * Raw password supplied during registration.
         * It will be encoded before being stored.
         * 
         * @Size: ensures the password length is between 8 and 100 characters.
         */
        @NotBlank
        @Size(min = 8, max = 100)
        String password) {

}
