package com.soundcloud.SoundCloudUsingSpringBoot.common.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class responsible for generating cryptographically
 * secure random tokens.
 *
 * These tokens can be used for password reset,
 * email verification, or any other feature requiring
 * unpredictable, URL-safe identifiers.
 */
public final class TokenGenerator {

    /**
     * Cryptographically secure random number generator.
     *
     * Reused for every token generation to avoid the cost
     * of creating a new SecureRandom instance each time.
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * Number of random bytes used for each generated token.
     *
     * 32 bytes = 256 bits of entropy.
     */
    private static final int TOKEN_SIZE = 32;

    /**
     * Prevent instantiation of this utility class.
     */
    private TokenGenerator() {
    }

    /**
     * Generates a cryptographically secure, URL-safe token.
     *
     * @return a random Base64 URL-safe token
     */
    public static String generate() {

        byte[] bytes = new byte[TOKEN_SIZE];

        // Fill the byte array with cryptographically secure random values.
        SECURE_RANDOM.nextBytes(bytes);

        return Base64 // Encoding method that converts binary data into 64 printable characters (A-Z, a-z, 0-9, +, and /).
                .getUrlEncoder() // Creates a Base64 encoder safe for use in URLs and file names.
                .withoutPadding() // Remove trailing '=' padding characters.
                .encodeToString(bytes); // Convert the random bytes into a URL-safe string.

    }

}
