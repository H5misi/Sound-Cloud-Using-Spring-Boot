package com.soundcloud.SoundCloudUsingSpringBoot.user.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundcloud.SoundCloudUsingSpringBoot.common.exception.BadRequestException;
import com.soundcloud.SoundCloudUsingSpringBoot.common.exception.ResourceNotFoundException;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.ProfileResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UpdateProfileRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;
import com.soundcloud.SoundCloudUsingSpringBoot.user.mapper.UserMapper;
import com.soundcloud.SoundCloudUsingSpringBoot.user.repository.UserRepository;

@Service 
public class UserServiceImpl implements UserService{
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    private User findUserByEmail(String email){

        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    private User findUserByUsername(String username){

        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", username));
    }

    @Override
    @Transactional(readOnly = true) // marks profile retrieval as a read-only transaction
    public UserResponse getMyProfile(String email) {

        User user = findUserByEmail(email);
        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getUserProfile(String username, String requesterEmail) {

        User user = findUserByUsername(username);

        // requesterEmail identifies the viewer who is searching for a specific account
        boolean isOwner = requesterEmail != null && user.getEmail().equals(requesterEmail);

        if (isOwner) {
            return userMapper.toUserResponse(user);
        }

        boolean showExtendedProfile = user.getProfileVisibility() == ProfileVisibility.PUBLIC;

        return userMapper.toUserProfileResponse(user, showExtendedProfile);
    }

    @Override
    @Transactional 
    public UserResponse updateMyProfile(String email, UpdateProfileRequest request) {
        
        if (request == null) {
            throw new  BadRequestException("Request body must not be null");
        }

        User user = findUserByEmail(email);

        

        validateRequest(request);

        applyChanges(user, request);

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }
    


    private void validateRequest(UpdateProfileRequest request){

        if (request.getDisplayName().isProvided()) {
            String value = request.getDisplayName().getValue();

            if (value == null || value.isBlank()) {
                throw new BadRequestException("DisplayName must not be blank");
            }
        }

        if (request.getBio().isProvided()) {
            String value = request.getBio().getValue();

            if (value != null && value.isBlank()) {
                throw new BadRequestException("Bio must not be blank");
            }
        }


        if (request.getLocation().isProvided()) {
            String value = request.getLocation().getValue();

            if (value != null && value.isBlank()) {
                throw new BadRequestException("Location must not be blank");
            }
        }

        // enums (accountType & profileVisibility) restrict values to the constants defined in the enum
        if (request.getAccountType().isProvided() 
                && request.getAccountType().getValue() == null) {
            throw new BadRequestException("accountType must not be null");
        }


        if (request.getProfileVisibility().isProvided() 
                && request.getProfileVisibility().getValue() == null) {
            throw new BadRequestException("profileVisibility must not be null");
        }

        if (request.getInstagramUrl().isProvided()) {
            validateUrl(request.getInstagramUrl().getValue(), "instagramUrl");
        }

        if (request.getTwitterUrl().isProvided()) {
            validateUrl(request.getTwitterUrl().getValue(), "twitterUrl");
        }
        
        if (request.getWebsiteUrl().isProvided()) {
            validateUrl(request.getWebsiteUrl().getValue(), "websiteUrl");
        }

        if (request.getFavoriteGenres().isProvided()) {
            Set<String> genres = request.getFavoriteGenres().getValue();

            if (genres != null) {
                
                for (String genre : genres) {

                    if (genre == null || genre.isBlank()) {
                        throw new  BadRequestException("favoriteGenres must not contain blank values");
                    }
                }
            }
        }

       
    }


    private void validateUrl(String value, String fieldName){

        if (value == null) {
            return;
        }

        try {
            URI uri = new URI(value);

            // check the schema (the part before : )
            String schema = uri.getScheme();
            
            boolean validSchema = schema != null
                    && (schema.equalsIgnoreCase("http") || schema.equalsIgnoreCase("https"));

            if (!validSchema || uri.getHost() == null || uri.getHost().isBlank()) {
                throw new BadRequestException(fieldName + " must be valid http / https URL");
            }


        } catch (URISyntaxException exception) {
            throw new BadRequestException(fieldName + " must be valid http / https URL");
        }
    }

    private void applyChanges(User user, UpdateProfileRequest request){

        if (request.getDisplayName().isProvided()) {
            user.setDisplayName(request.getDisplayName().getValue());
        }

        if (request.getBio().isProvided()) {
            user.setBio(request.getBio().getValue());
        }

        if (request.getLocation().isProvided()) {
            user.setLocation(request.getLocation().getValue());
        }

        if (request.getAccountType().isProvided()) {
            user.setAccountType(request.getAccountType().getValue());
        }

        if (request.getProfileVisibility().isProvided()) {
            user.setProfileVisibility(request.getProfileVisibility().getValue());
        }

        if (request.getInstagramUrl().isProvided()) {
            user.setInstagramUrl(request.getInstagramUrl().getValue());
        }

        if (request.getTwitterUrl().isProvided()) {
            user.setTwitterUrl(request.getTwitterUrl().getValue());
        }

        if (request.getWebsiteUrl().isProvided()) {
            user.setWebsiteUrl(request.getWebsiteUrl().getValue());
        }

        if (request.getFavoriteGenres().isProvided()) {
            Set<String> favoriteGenres = request.getFavoriteGenres().getValue();
            // Hibernate manages @ElementCollection, so replacing its collection object can cause persistence issues
            // So instead, we clear the current collection, and add the new one if the set above or clear if it's null
            user.getFavoriteGenres().clear();

            if (favoriteGenres != null) {
                
                user.getFavoriteGenres().addAll(favoriteGenres);
            }
        }
    }

}
