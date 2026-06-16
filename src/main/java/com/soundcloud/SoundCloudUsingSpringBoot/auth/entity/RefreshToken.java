package com.soundcloud.SoundCloudUsingSpringBoot.auth.entity;

import java.time.LocalDateTime;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * Represents a refresh token used to obtain new access tokens
 * without requiring the user to authenticate again.
 *
 * Each refresh token belongs to a single user and expires
 * after a defined period of time. ( user 1---* tokens)
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    
    
    /**
     * Unique identifier for the refresh token record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    /**
     * The refresh token value used to request
     * a new access token.
     */
    @Column(unique = true, nullable = false)
    private String token;


    /**
     * The owner of this refresh token.
     *
     * @ManyToOne -> A user may have multiple refresh tokens
     * for different active sessions/devices.
     *
     * FetchType.LAZY prevents loading the entire
     * User entity unless it is explicitly accessed.
     * 
     * @JoinColumn -> creates the foreign key column
     * "user_id" which references users.id.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    /**
     * The date and time when this refresh token expires.
     *
     * Expired tokens can no longer be used to obtain
     * new access tokens.
     */
    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
