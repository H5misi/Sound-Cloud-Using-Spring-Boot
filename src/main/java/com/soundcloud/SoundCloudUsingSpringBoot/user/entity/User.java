package com.soundcloud.SoundCloudUsingSpringBoot.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user account in the SoundCloud application.
 *
 * This entity stores authentication information, profile details,
 * account settings, and auditing metadata.
 */

@Entity // Marks this class as a JPA entity.
@Getter
@Setter
@NoArgsConstructor // Required by JPA to instantiate the entity.
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;


    /**
     * Creates a new User entity for account registration.
     *
     * This constructor initializes only the fields required when a new
     * user account is created. Database-managed fields (such as the ID
     * and timestamps) and optional profile information are left to their
     * default values.
     *
     * Annotated with {@Builder} to provide a readable and maintainable
     * way of constructing new User instances.
     */
    @Builder
    public User(String username,
            String email,
            String password,
            String displayName,
            AccountType accountType,
            ProfileVisibility profileVisibility,
            VerificationStatus verificationStatus,
            SuspensionStatus suspensionStatus) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.accountType = accountType;
        this.profileVisibility = profileVisibility;
        this.verificationStatus = verificationStatus;
        this.suspensionStatus = suspensionStatus;
    }

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;

    private String bio;

    private String location;

    private String profileImageUrl;

    private String coverImageUrl;

    private String instagramUrl;

    private String twitterUrl;

    private String websiteUrl;

    /**
     * Indicates whether the account belongs to
     * an artist or a listener.
     *
     * @Enumerated(EnumType.STRING) stores the enum name as string (EnumType.STRING)
     *                              (e.g. ARTIST, LISTENER) instead of its numeric
     *                              position.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    /**
     * Determines whether the profile is publicly visible.
     *
     * @Enumerated(EnumType.STRING) stores the enum name as string
     *                              in the database instead of its ordinal value.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileVisibility profileVisibility;

    /**
     * Determines whether the account is verified or pending verification.
     * 
     * @Enumerated(EnumType.STRING) stores the enum name as string
     *                              in the database instead of its ordinal value.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    /**
     * Determines whether the account is active or suspended by an administrator.
     *
     * @Enumerated(EnumType.STRING) stores the enum name as string
     *                              in the database instead of its ordinal value.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SuspensionStatus suspensionStatus;

    /**
     * Timestamp when the user account was created.
     *
     * @CreationTimestamp automatically sets the value
     *                    when the entity is first persisted.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last modification made to the account.
     *
     * @UpdateTimestamp automatically updates the value
     *                  whenever the entity is modified.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
