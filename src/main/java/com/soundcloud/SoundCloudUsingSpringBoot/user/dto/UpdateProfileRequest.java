package com.soundcloud.SoundCloudUsingSpringBoot.user.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.AccountType;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;

public class UpdateProfileRequest {

    // Start the fields as not provided

    private PatchField<String> displayName = PatchField.undefined();
    private PatchField<String> bio = PatchField.undefined();
    private PatchField<String> location = PatchField.undefined();

    private PatchField<AccountType> accountType = PatchField.undefined();
    private PatchField<ProfileVisibility> profileVisibility = PatchField.undefined();

    private PatchField<String> instagramUrl = PatchField.undefined();
    private PatchField<String> twitterUrl = PatchField.undefined();
    private PatchField<String> websiteUrl = PatchField.undefined();

    private PatchField<Set<String>> favoriteGenres = PatchField.undefined();



    public PatchField<String> getDisplayName() {
        return displayName;
    }

    /**
     * @JsonSetter is a Jackson annotation (tool / java library)
     * it tells jackson to call this method when the JSON contains the "<property_name>" 
     */ 
    //  
    @JsonSetter("displayName")
    public void setDisplayName(String value) {
        this.displayName = PatchField.of(value);
    }

    public PatchField<String> getBio() {
        return bio;
    }

    @JsonSetter("bio")
    public void setBio(String value) {
        this.bio = PatchField.of(value);
    }

    public PatchField<String> getLocation() {
        return location;
    }

    @JsonSetter("location")
    public void setLocation(String value) {
        this.location = PatchField.of(value);
    }

    public PatchField<AccountType> getAccountType() {
        return accountType;
    }

    @JsonSetter("accountType")
    public void setAccountType(AccountType value) {
        this.accountType = PatchField.of(value);
    }

    public PatchField<ProfileVisibility> getProfileVisibility() {
        return profileVisibility;
    }

    @JsonSetter("profileVisibility")
    public void setProfileVisibility(ProfileVisibility value) {
        this.profileVisibility = PatchField.of(value);
    }

    public PatchField<String> getInstagramUrl() {
        return instagramUrl;
    }

    @JsonSetter("instagramUrl")
    public void setInstagramUrl(String value) {
        this.instagramUrl = PatchField.of(value);
    }

    public PatchField<String> getTwitterUrl() {
        return twitterUrl;
    }

    @JsonSetter("twitterUrl")
    public void setTwitterUrl(String value) {
        this.twitterUrl = PatchField.of(value);
    }

    public PatchField<String> getWebsiteUrl() {
        return websiteUrl;
    }

    @JsonSetter("websiteUrl")
    public void setWebsiteUrl(String value) {
        this.websiteUrl = PatchField.of(value);
    }

    public PatchField<Set<String>> getFavoriteGenres() {
        return favoriteGenres;
    }

    @JsonSetter("favoriteGenres")
    public void setFavoriteGenres(Set<String> value) {
        this.favoriteGenres = PatchField.of(value);
    }


    
}
