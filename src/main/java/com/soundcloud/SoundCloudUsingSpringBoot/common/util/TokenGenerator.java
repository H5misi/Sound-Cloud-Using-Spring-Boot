package com.soundcloud.SoundCloudUsingSpringBoot.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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



    /**
     * Computes the SHA-256 hash of a token.
     *
     * The resulting hash is represented as a hexadecimal string
     * suitable for database storage and comparison.
     *
     * @param token the raw token to hash
     * @return the hexadecimal SHA-256 hash of the token
     */
    public static String hash(String token) {
        try {
            // Create a MessageDigest configured to use the SHA-256 algorithm.
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            /**
             * Compute the SHA-256 of the UTF-8 encoded token.
             * 
             * token.getBytes(StandardCharsets.UTF_8):
             * converts the Java String into a sequence of bytes
             * before it can be processed by the hashing algorithm.
             */
            byte[] hashBytes = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));

            // Convert the hash bytes into a hexadecimal string.
            StringBuilder hexadecimalHash = new StringBuilder();

            for (byte b : hashBytes) {
                hexadecimalHash.append(String.format("%02x", b));
            }

            // Return the hexadecimal representation of the hash.
            // Strings are easier to persist, compare, and query than raw byte arrays.
            return hexadecimalHash.toString();

        } catch (NoSuchAlgorithmException exception) {
            // SHA-256 is guaranteed to exist in every modern Java runtime.
            // Reaching this point indicates a JVM configuration problem.
            throw new IllegalStateException("SHA-256 algorithm is not available.", exception);
        }
        

    
    }

}
