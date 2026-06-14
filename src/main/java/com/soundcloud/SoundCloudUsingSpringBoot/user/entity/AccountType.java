package com.soundcloud.SoundCloudUsingSpringBoot.user.entity;


/**
 * Represents the type of account a user owns.
 *
 * Users can register either as:
 * - ARTIST: creator account
 * - LISTENER: consumer account
 */
public enum AccountType {
    ARTIST,
    LISTENER
}
