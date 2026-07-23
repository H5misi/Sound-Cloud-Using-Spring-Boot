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
     * Finds a password reset token by its token value.
     *
     * @param token the password reset token value
     * @return an Optional containing the password reset token if found,
     *         otherwise an empty Optional
     */
    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);
}
