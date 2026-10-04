package com.soundcloud.SoundCloudUsingSpringBoot.auth.service;

import com.soundcloud.SoundCloudUsingSpringBoot.user.dto.UserSummaryResponse;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.ForgotPasswordRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.LoginRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.LoginResponse;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.RegisterRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.dto.ResetPasswordRequest;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.email.EmailService;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.entity.PasswordResetToken;
import com.soundcloud.SoundCloudUsingSpringBoot.auth.repository.PasswordResetTokenRepository;
import com.soundcloud.SoundCloudUsingSpringBoot.common.exception.BadRequestException;
import com.soundcloud.SoundCloudUsingSpringBoot.common.util.TokenGenerator;
import com.soundcloud.SoundCloudUsingSpringBoot.security.JwtService;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.SuspensionStatus;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.VerificationStatus;
import com.soundcloud.SoundCloudUsingSpringBoot.user.mapper.UserMapper;
import com.soundcloud.SoundCloudUsingSpringBoot.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final UserMapper userMapper;

    @Value("${app.frontend-url}")
    private String frontendUrl;

    /**
     * @Transactional Executes the registration process within a single database
     *                transaction.
     *
     *                If any database operation fails during registration, 
     *                all changes are rolled back to preserve data consistency.
     */
    @Transactional
    @Override
    public LoginResponse register(RegisterRequest request) {

        // 1. Check business rules and ensure the email and username are not already in use.
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email is already registered.");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username is already taken.");
        }

        // 2. Encode the raw password before persisting it.
        String encodedPassword = passwordEncoder.encode(request.password());

        // 3. Map registration request to full User entity.
        User user = userMapper.toEntity(request, encodedPassword);

        // 4. Persist the new user and obtain the managed entity.
        user = userRepository.save(user);

        // 5. Generate JWT access token
        String accessToken = jwtService.generateToken(user);

        // 6. Build response

        UserSummaryResponse summaryResponse = userMapper.toSummaryResponse(user);

        return new LoginResponse(accessToken, "Bearer", summaryResponse);
    }

    public LoginResponse login(LoginRequest request) {
        // 1- Authenticate the user's credentials using spring security.
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        // 2- Retrieve the authenticated user from the database.
        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Invalid email or password"));

        // 3- Check account status and verification status.
        if (user.getSuspensionStatus() == SuspensionStatus.SUSPENDED) {
            throw new BadRequestException("Your account has been suspended.");
        }

        if (user.getVerificationStatus() == VerificationStatus.PENDING) {
            throw new BadRequestException("Please verify your email before logging in.");
        }

        // 4- Generate JWT token.
        String accessToken = jwtService.generateToken(user);

        // 5- Build the response.
        return new LoginResponse(
                accessToken,
                "Bearer",
                userMapper.toSummaryResponse(user));

    }

    @Override 
    @Transactional 
    public void forgotPassword(ForgotPasswordRequest request){
        
        Optional<User> optionalUser = userRepository.findByEmail(request.email());

        if (optionalUser.isEmpty()) {
            // The return message is customized in the controller because this method return type is void
            // the message: "If an account exists with this email, a password reset link has been sent.";
            return;
        }

        User user = optionalUser.get();

        // Generate a new token, this raw token will sent to the user.
        String rawToken = TokenGenerator.generate();

        // Store only the SHA-256 hash of the token in the database.
        String hashedToken = TokenGenerator.hash(rawToken);

        // Reuse the existing reset request so that only the latest token remains valid.
        PasswordResetToken resetToken = passwordResetTokenRepository.findByUser(user)
                .orElseGet(PasswordResetToken::new);

        resetToken.setUser(user);
        resetToken.setTokenHash(hashedToken);
        resetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));

        passwordResetTokenRepository.save(resetToken);


        // Build the URL containing the raw token that was sent to the user.
        String resetLink = frontendUrl + "/reset-password?token=" + rawToken;

        emailService.sendEmail(
            user.getEmail(),
            "Reset your SoundCloud password",
            "Click the following link to reset your password: " + resetLink);


            // The return message is customized in the controller because this method return type is void
            // the message: "If an account exists with this email, a password reset link has been sent.";        
            return;



    }


    @Override 
    @Transactional // Ensures the password update and reset-token deletion succeed or roll back together.
    public void resetPassword(ResetPasswordRequest request){

        // Hash the raw token received from the client before looking it up.
        String hashedToken = TokenGenerator.hash(request.token());

        // Find the password reset request associated with the token
        PasswordResetToken resetToken = passwordResetTokenRepository.findByTokenHash(hashedToken)
                .orElseThrow(() -> new BadRequestException("Invalid or expired password reset token."));


        // Reject the request if the reset token is expired.
        if (resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Invalid or expired password reset token.");
        }

        // Get the user associated with the valid password reset token.
        User user = resetToken.getUser();


        // Encode the new password before storing it.
        String encodedPassword = passwordEncoder.encode(request.newPassword());

        // Set the new password and save it.
        user.setPassword(encodedPassword);

        userRepository.save(user);


        // Delete the reset request token so the token can't be used again.
        passwordResetTokenRepository.delete(resetToken);


    }

}
