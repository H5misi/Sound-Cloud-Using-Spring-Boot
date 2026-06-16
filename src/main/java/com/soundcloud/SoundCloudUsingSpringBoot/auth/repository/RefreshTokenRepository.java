package com.soundcloud.SoundCloudUsingSpringBoot.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.entity.RefreshToken;


/**
 * Repository responsible for RefreshToken persistence operations.
 *
 * Extends JpaRepository to inherit standard CRUD operations
 * and defines custom query methods required by the authentication flow.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Finds a refresh token by its token value.
     *
     * @param token the refresh token value
     * @return an Optional containing the refresh token if found,
     *         otherwise an empty Optional
     */
    Optional<RefreshToken> findByToken(String token);

}
