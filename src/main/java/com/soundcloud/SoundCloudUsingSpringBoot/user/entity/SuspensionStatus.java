package com.soundcloud.SoundCloudUsingSpringBoot.user.entity;


/**
 * Represents the suspension status of a user account.
 *
 * Accounts can be:
 * - ACTIVE: account is allowed to access the platform.
 * - SUSPENDED: account access is restricted by an administrator.
 */
public enum SuspensionStatus {
    ACTIVE,
    SUSPENDED
}
