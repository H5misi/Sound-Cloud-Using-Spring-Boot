package com.soundcloud.SoundCloudUsingSpringBoot.auth.service;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.ForgotPasswordRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.LoginRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.LoginResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.RegisterRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.ResetPasswordRequest;

/**
 * Defines authentication-related business operations.
 *
 * Responsible for:
 * - user registration
 * - user authentication
 * - password reset workflow
 */
public interface AuthService {

    /**
     * Registers a new user account and authenticates
     * the newly created user.
     *
     * @param request registration request
     * @return authentication response containing the generated JWT
     */
    LoginResponse register(RegisterRequest request);

    /**
     * Authenticates an existing user.
     *
     * @param request login credentials
     * @return authentication response containing the generated JWT
     */
    LoginResponse login(LoginRequest request);

    /**
     * Initiates the password reset workflow.
     *
     * If the email belongs to an existing account,
     * a password reset link will be sent.
     *
     * The method intentionally behaves the same whether
     * the email exists or not to prevent user enumeration.
     *
     * @param request forgot password request
     */
    void forgotPassword(ForgotPasswordRequest request);

    /**
     * Resets a user's password using a valid reset token.
     *
     * @param request reset password request
     */
    void resetPassword(ResetPasswordRequest request);
}
