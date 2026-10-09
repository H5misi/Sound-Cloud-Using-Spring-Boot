package com.soundcloud.SoundCloudUsingSpringBoot.user.dto;

import java.util.Set;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.AccountType;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;

public record UserResponse(
    
    Long id,
    String username,
    String displayName,
    String bio,
    String location,
    AccountType accountType,
    ProfileVisibility profileVisibility,
    String profileImageUrl,
    String coverImageUrl,
    String instagramUrl,
    String twitterUrl,
    String websiteUrl,
    Set<String> favoriteGenres) {
}
