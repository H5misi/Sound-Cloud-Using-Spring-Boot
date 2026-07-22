package com.soundcloud.SoundCloudUsingSpringBoot.auth.email;

/**
 * Defines operations for sending emails.
 */
public interface EmailService {

    /**
     * Sends an email.
     *
     * @param to recipient email address
     * @param subject email subject
     * @param body email body
     */
    void sendEmail(
            String to,
            String subject,
            String body
    );
}
