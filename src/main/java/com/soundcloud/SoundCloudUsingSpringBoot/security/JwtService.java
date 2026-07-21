package com.soundcloud.SoundCloudUsingSpringBoot.security;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;


/**
 * Defines operations for generating and reading JSON Web Tokens (JWT).
 *
 * Implementations are responsible for creating signed JWTs and
 * extracting authenticated user information from them.
 */
public interface JwtService {

    /**
     * Generates a signed JWT access token for the given user.
     *
     * @param user the authenticated user
     * @return a signed JWT access token
     */
    String generateToken(User user);

    /**
     * Extracts the user's email from a JWT access token.
     *
     * @param token the JWT access token
     * @return the authenticated user's email
     */
    String getEmailFromToken(String token);

}
