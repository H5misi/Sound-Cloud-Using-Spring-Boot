package com.soundcloud.SoundCloudUsingSpringBoot.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * Request body used to complete a password reset.
 *
 * The provided reset token proves the user's identity,
 * allowing a new password to be chosen.
 * 
 * Password reset strategy:
 * SHA-256 hashed token 
 * 
 * The SHA-256 hash of the token is stored together with:
 * - User ID
 * - Expiration time
 * - Creation time
 * - Used flag
 * 
 */
public record ResetPasswordRequest(

        /**
         * Password reset token received via email.
         */
        @NotBlank
        String token,


        /**
         * New password chosen by the user.
         */
        @NotBlank
        @Size(min = 8, max = 100)
        String newPassword) {

}
