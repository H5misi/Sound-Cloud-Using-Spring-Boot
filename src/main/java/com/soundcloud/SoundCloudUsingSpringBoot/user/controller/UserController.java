package com.soundcloud.SoundCloudUsingSpringBoot.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.ProfileResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UpdateProfileRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {

        // getName return email instead username because we return email instead of
        // username in Spring Security's UserDetails
        String email = authentication.getName();

        UserResponse response = userService.getMyProfile(email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getUserProfile(
            // @PathVariable because the username is part of the URL (path variable, with
            // placeholder in the path)
            @PathVariable String username, Authentication authentication) {

        /**
         * Ternary operator:
         * 
         * authentication != null ? authentication.getName() : null
         * 
         * is shorthand to:
         * 
         * if (authentication != null){
         * return authentication.getName();
         * } else {
         * return null;
         * }
         * 
         * -> the authentication is optional, so requesterEmail may be null because the
         * requester is anonymous
         * 
         */
        String requesterEmail = authentication != null ? authentication.getName() : null;

        // return ProfileResponse so UserServiceImpl decide which response depending if the requester is the owner or not
        ProfileResponse response = userService.getUserProfile(username, requesterEmail);
        return ResponseEntity.ok(response);
    }


    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            // @RequestBody tells spring to deserialize the incoming JSON into an
            // UpdateProfileRequest object using Jackson.
            @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        
        String email = authentication.getName();

        UserResponse response = userService.updateMyProfile(email, request);
        return ResponseEntity.ok(response);
    }
}
