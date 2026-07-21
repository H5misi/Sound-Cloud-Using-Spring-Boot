package com.soundcloud.SoundCloudUsingSpringBoot.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Spring Security filter responsible for authenticating
 * incoming HTTP requests using JSON Web Tokens (JWT).
 *
 * For every request, this filter:
 * - extracts the JWT from the Authorization header
 * - validates the token
 * - loads the corresponding user
 * - creates an Authentication object
 * - stores it in the SecurityContext
 */
@Component
@RequiredArgsConstructor // Generates a constructor for all final fields.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Standard HTTP header used to send authentication credentials.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Authentication scheme expected by this application.
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Provides JWT parsing and validation operations.
     */
    private final JwtServiceImpl jwtServiceImpl;

    /**
     * Loads application users as Spring Security UserDetails.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Authenticates every incoming HTTP request that contains
     * a valid JWT access token.
     *
     * @param request     incoming HTTP request
     * @param response    outgoing HTTP response
     * @param filterChain remaining filters in the Spring Security chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        // Read the Authorization header from the incoming request.
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        // Continue the request if no Bearer token was provided
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove the "Bearer" prefix to obtain the raw JWT
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());

        // Extract the authenticated user's email from the token
        String email = jwtServiceImpl.getEmailFromToken(token);

        // Authenticate the request only if it has not already authenticated the current
        // user.
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the corresponding user from database as a UserDetails object
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Create a Spring Security authentication object.
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, // The authenticated user
                    null, // Credentials no longer needed after JWT validation.
                    userDetails.getAuthorities()); // User roles/authorities.

            // Store the authenticated user for the current request.
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        // Continue processing the remaining filters and eventually the controller.
        filterChain.doFilter(request, response);

    }

}
