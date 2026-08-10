package com.soundcloud.SoundCloudUsingSpringBoot.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.entity.PasswordResetToken;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

/**
 * Repository responsible for PasswordResetToken persistence operations.
 *
 * Extends JpaRepository to inherit standard CRUD operations
 * and defines custom query methods required by the password
 * recovery and reset flow.
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {


    /**
     * Finds an active password reset request by the hash
     * of its password reset token.
     *
     * @param tokenHash the SHA-256 hash of the password reset token
     * @return an Optional containing the matching password reset request,
     *         or an empty Optional if none exists
     */
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);


    /**
     * Finds the active password reset request associated with a user.
     *
     * Since each user can have only one active password reset request,
     * this method returns at most one entity.
     *
     * @param user the user who requested a password reset
     * @return an Optional containing the active password reset request,
     *         or an empty Optional if none exists
     */
    Optional<PasswordResetToken> findByUser(User user);
}
