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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a password reset token used to allow users
 * to securely reset their account password.
 *
 * Each user can have only one active password reset token
 * at a time. Password reset tokens expire after a defined
 * period and are removed once the password is successfully reset.
 */
@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {

    /**
     * Unique identifier for the password reset token record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * SHA-256 hash of the password reset token.
     *
     * The raw token is sent to the user's email and is never
     * stored in the database. During password reset, the
     * received token is hashed and compared against this value.
     */
    @Column(unique = true, nullable = false)
    private String tokenHash;

    /**
     * The user associated with this password reset token.
     *
     * @OneToOne indicates that a user can have only one
     *           active password reset token at a time.
     *
     *           FetchType.LAZY prevents loading the entire User
     *           entity unless it is explicitly accessed.
     *
     * @JoinColumn creates the foreign key column
     *             "user_id" which references users.id.
     *
     *             unique = true ensures that a user cannot have
     *             multiple active password reset tokens.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    /**
     * The date and time when this password reset token expires.
     *
     * Expired password reset tokens can no longer be used
     * to reset a user password.
     */
    @Column(nullable = false)
    private LocalDateTime expiresAt;

}
