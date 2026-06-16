package com.soundcloud.SoundCloudUsingSpringBoot.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

/**
 * Repository responsible for User persistence operations.
 *
 * Extends JpaRepository to inherit standard CRUD operations
 * and defines custom query methods required by the application.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks whether a username is already in use.
     *
     * @param username the username to check
     * @return true if the username exists, otherwise false
     */
    boolean existsByUsername(String username);

    /**
     * Checks whether an email address is already in use.
     *
     * @param email the email address to check
     * @return true if the email exists, otherwise false
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found,
     *         otherwise an empty Optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the user if found,
     *         otherwise an empty Optional
     */
    Optional<User> findByEmail(String email);
}
