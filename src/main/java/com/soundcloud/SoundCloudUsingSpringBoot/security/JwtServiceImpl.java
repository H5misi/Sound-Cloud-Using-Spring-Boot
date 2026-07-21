package com.soundcloud.SoundCloudUsingSpringBoot.security;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Service implementation responsible for generating,
 * parsing, and validating JSON Web Tokens (JWT)
 * used for authentication.
 *
 * Creates signed access tokens and extracts
 * authenticated user information from JWTs.
 */
@Service
public class JwtServiceImpl implements JwtService {

    /**
     * Secret key used to sign and validate JWT tokens.
     *
     * Loaded from application.properties file.
     */
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    /**
     * Access token lifetime in milliseconds.
     *
     * Loaded from application.properties file.
     */
    @Value("${jwt.expiration-time-ms}")
    private long jwtExpirationMs;

    /**
     * Creates a cryptographic signing key from the configured
     * JWT secret.
     *
     * The secret is stored as a Base64-encoded string in the
     * application configuration. It is decoded into its original
     * bytes and converted into a SecretKey that can be used for
     * signing and validating JWT tokens.
     *
     * @return the JWT signing key
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a signed JWT access token for the given user.
     *
     * @param user the authenticated user
     * @return a signed JWT token
     */
    @Override
    public String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(user.getEmail()) // store the user email in (sub claim) (subject)
                .issuedAt(now) // store the token creation time in (iat claim)
                .expiration(expiration) // store the expiration time in (exp claim)
                .signWith(getSigningKey()) // sign the token using the secret key
                .compact(); // build the final compact JWT string
    }

    /**
     * Extracts the user email from a JWT token.
     *
     * The email is stored as the JWT subject when the token
     * is generated.
     *
     * @param token the JWT token
     * @return the email stored in the token
     */
    @Override
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Parses and validates a JWT token, then returns its claims.
     *
     * During parsing, the JWT library automatically verifies:
     * - the token signature
     * - the token expiration time
     * - the token structure
     *
     * If validation fails, a JWT-related exception is thrown.
     *
     * @param token the JWT token
     * @return the claims stored in the token
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // verify the JWT signature with the secret key
                .build() // build the JWT configured parser (return JwtParser instead of
                         // JwtParserBuilder)
                .parseSignedClaims(token) // parses the JWT, validate its signature & expiration, return signed claims
                .getPayload(); // extract and return the JWT payload (claims)
    }

}
