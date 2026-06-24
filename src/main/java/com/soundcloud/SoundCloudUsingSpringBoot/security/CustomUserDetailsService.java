package com.soundcloud.SoundCloudUsingSpringBoot.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;
import com.soundcloud.SoundCloudUsingSpringBoot.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Spring Security service responsible for loading
 * application users during the authentication process.
 *
 * Acts as a bridge between Spring Security and the
 * application's user persistence layer (UserRepository).
 * 
 * - CustomUserDetailsService -> return UserDetails object
 * - UserRepository -> return User object
 */
@Service
@RequiredArgsConstructor // Used to automate Constructor-Based Dependency Injection (Generates a constructor for all final fields)
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repository used to retrieve users from the database.
     */
    private final UserRepository userRepository;

    /**
     * Loads a user by email and converts it into a Spring Security
     * UserDetails object.
     *
     * Although the method name references "username", this application
     * uses email as the authentication identifier.
     *
     * @param email the user's email address
     * @return a Spring Security UserDetails object
     * @throws UsernameNotFoundException if no user exists with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        
        /**
         * Retrieve the user by the email
         * or throw an exception if no matching user exists.
         */
        User user = userRepository
                .findByEmail(email) // Retrieve the user from the database by email.
                .orElseThrow(() -> // Throw an exception if no user exists with this email.
                new UsernameNotFoundException(
                        "User not found with email: " + email));

        /**
         * UserDetails represents authentication information and Spring Security
         * expects it to contain an authentication identifier, password,
         * and authorities.
         */
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // Authentication identifier (email instead username)
                user.getPassword(), // Encoded password stored in database
                Collections.emptyList() // No authorities/roles implemented yet
        );

    }

}
