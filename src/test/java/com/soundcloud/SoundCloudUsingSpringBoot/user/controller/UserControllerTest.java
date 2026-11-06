package com.soundcloud.SoundCloudUsingSpringBoot.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.soundcloud.SoundCloudUsingSpringBoot.security.CustomUserDetailsService;
import com.soundcloud.SoundCloudUsingSpringBoot.security.JwtService;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UpdateProfileRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserProfileResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.AccountType;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;
import com.soundcloud.SoundCloudUsingSpringBoot.user.service.UserService;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
    private static final String USER_EMAIL = "john@example.com";
    private static final String USERNAME = "john";

    

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    
    @MockitoBean
    private JwtService jwtService;
    
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldReturnAuthenticatedUserProfile() throws Exception {

        UserResponse response = createUserResponse();

        when(userService.getMyProfile(USER_EMAIL))
                .thenReturn(response);

        mockMvc.perform(get("/users/me")
                        .principal(createAuthentication(USER_EMAIL)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.displayName").value("John Doe"))
                .andExpect(jsonPath("$.bio").value("Backend engineer"));

        verify(userService).getMyProfile(USER_EMAIL);
    }

    // --------------------------------------------------
    // GET /users/{username}
    // --------------------------------------------------

    @Test
    void shouldReturnPublicProfileForAnonymousUser() throws Exception {

        UserProfileResponse response = createUserProfileResponse(
                ProfileVisibility.PUBLIC);

        when(userService.getUserProfile(USERNAME, null))
                .thenReturn(response);

        mockMvc.perform(get("/users/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.displayName").value("John Doe"))
                .andExpect(jsonPath("$.location").value("Cairo"));

        verify(userService).getUserProfile(USERNAME, null);
    }

    @Test
    void shouldReturnPrivateProfileForAnonymousUser() throws Exception {

        UserProfileResponse response = createUserProfileResponse(
                ProfileVisibility.PRIVATE);

        when(userService.getUserProfile(USERNAME, null))
                .thenReturn(response);

        mockMvc.perform(get("/users/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.profileVisibility").value("PRIVATE"));

        verify(userService).getUserProfile(USERNAME, null);
    }

    @Test
    void shouldReturnOwnerProfileWhenRequesterIsOwner() throws Exception {

        UserResponse response = createUserResponse();

        when(userService.getUserProfile(USERNAME, USER_EMAIL))
                .thenReturn(response);

        mockMvc.perform(get("/users/{username}", USERNAME)
                        .principal(createAuthentication(USER_EMAIL)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.email").doesNotExist());

        verify(userService).getUserProfile(USERNAME, USER_EMAIL);
    }

    @Test
    void shouldReturnProfileForAuthenticatedVisitor() throws Exception {

        UserProfileResponse response = createUserProfileResponse(
                ProfileVisibility.PUBLIC);

        when(userService.getUserProfile(USERNAME, "visitor@example.com"))
                .thenReturn(response);

        mockMvc.perform(get("/users/{username}", USERNAME)
                        .principal(createAuthentication("visitor@example.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME));

        verify(userService).getUserProfile(
                USERNAME, "visitor@example.com");
    }

    // --------------------------------------------------
    // PATCH /users/me
    // --------------------------------------------------

    @Test
    void shouldUpdateAuthenticatedUserProfile() throws Exception {

        UserResponse response = createUserResponse();

        String requestBody = """
                {
                    "displayName": "John Updated",
                    "bio": "Updated backend engineer"
                }
                """;

        when(userService.updateMyProfile(
                eq(USER_EMAIL),
                any(UpdateProfileRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/users/me")
                        .principal(createAuthentication(USER_EMAIL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.displayName").value("John Doe"));

        verify(userService).updateMyProfile(
                eq(USER_EMAIL),
                any(UpdateProfileRequest.class));
    }

    @Test
    void shouldAcceptEmptyProfileUpdate() throws Exception {

        UserResponse response = createUserResponse();

        when(userService.updateMyProfile(
                eq(USER_EMAIL),
                any(UpdateProfileRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/users/me")
                        .principal(createAuthentication(USER_EMAIL))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

        verify(userService).updateMyProfile(
                eq(USER_EMAIL),
                any(UpdateProfileRequest.class));
    }

    // --------------------------------------------------
    // Test helpers
    // --------------------------------------------------

    private UsernamePasswordAuthenticationToken createAuthentication(
            String email) {

        return new UsernamePasswordAuthenticationToken(
                email, null);
    }

    private UserResponse createUserResponse() {

        return new UserResponse(
                1L,
                USERNAME,
                "John Doe",
                "Backend engineer",
                "Cairo",
                AccountType.ARTIST,
                ProfileVisibility.PUBLIC,
                "profile.jpg",
                "cover.jpg",
                "https://instagram.com/john",
                "https://twitter.com/john",
                "https://john.com",
                Set.of("Rock", "Jazz")
        );
    }

    private UserProfileResponse createUserProfileResponse(
            ProfileVisibility visibility) {

        return new UserProfileResponse(
                USERNAME,
                "John Doe",
                "Backend engineer",
                visibility == ProfileVisibility.PUBLIC ? "Cairo" : null,
                AccountType.ARTIST,
                visibility,
                "profile.jpg",
                "cover.jpg",
                visibility == ProfileVisibility.PUBLIC
                        ? "https://instagram.com/john" : null,
                visibility == ProfileVisibility.PUBLIC
                        ? "https://twitter.com/john" : null,
                visibility == ProfileVisibility.PUBLIC
                        ? "https://john.com" : null,
                visibility == ProfileVisibility.PUBLIC
                        ? Set.of("Rock", "Jazz") : null
        );
    }
}
