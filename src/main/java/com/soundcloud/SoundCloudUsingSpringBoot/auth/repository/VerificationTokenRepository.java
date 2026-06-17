package com.soundcloud.SoundCloudUsingSpringBoot.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.entity.VerificationToken;

/**
 * Repository responsible for VerificationToken persistence operations.
 *
 * Extends JpaRepository to inherit standard CRUD operations
 * and defines custom query methods required by the verification flow.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    /**
     * Finds a verification token by its token value.
     *
     * @param token the verification token value
     * @return an Optional containing the verification token if found,
     *         otherwise an empty Optional
     */
    Optional<VerificationToken> findByToken(String token);
}
