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
 * Represents a verification token used to verify a newly
 * registered user account.
 *
 * Each user can have only one active verification token
 * at a time. Verification tokens expire after a defined
 * period and are removed once the account is verified.
 */
@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {


    /**
     * Unique identifier for the verification token record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The verification token value sent to the user
     * via email during account verification.
     */
    @Column(unique = true, nullable = false)
    private String token;

    /**
     * The user associated with this verification token.
     *
     * @OneToOne indicates that a user can have only one
     *           active verification token at a time.
     *
     *           FetchType.LAZY prevents loading the entire User
     *           entity unless it is explicitly accessed.
     *
     * @JoinColumn creates the foreign key column
     *             "user_id" which references users.id.
     *
     *             unique = true ensures that a user cannot have
     *             multiple active verification tokens.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    /**
     * The date and time when this verification token expires.
     *
     * Expired verification tokens can no longer be used
     * to verify a user account.
     */
    @Column(nullable = false)
    private LocalDateTime expiresAt;
}
