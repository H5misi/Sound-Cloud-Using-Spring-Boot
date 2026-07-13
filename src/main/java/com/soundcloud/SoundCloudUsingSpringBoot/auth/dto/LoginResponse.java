package com.soundcloud.SoundCloudUsingSpringBoot.auth.dto;

import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserSummaryResponse;

/**
 * Response returned after successful authentication.
 *
 * Contains the generated JWT access token and a lightweight
 * representation of the authenticated user.
 */
public record LoginResponse(

        /**
         * JWT access token used to authenticate subsequent requests.
         *
         * The client should include this token in the Authorization
         * header using the Bearer authentication scheme.
         */
        String accessToken,

        /**
         * Authentication scheme used for the access token.
         *
         * Current value: "Bearer".
         * This application currently uses the Bearer scheme.
         */
        String tokenType,

        // Basic information about the authenticated user.
        UserSummaryResponse user

) {

}
