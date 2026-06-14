package com.soundcloud.SoundCloudUsingSpringBoot.user.entity;


/**
 * Represents the verification status of a user account.
 *
 * Accounts can be:
 * - PENDING: waiting for email verification.
 * - VERIFIED: email verification completed successfully.
 */
public enum VerificationStatus {
    PENDING,
    VERIFIED
}
