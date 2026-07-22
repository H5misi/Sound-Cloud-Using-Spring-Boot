package com.soundcloud.SoundCloudUsingSpringBoot.auth.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.soundcloud.SoundCloudUsingSpringBoot.common.exception.EmailDeliveryException;

import lombok.RequiredArgsConstructor;

/**
 * Sends emails using an SMTP server.
 */
@Service
@RequiredArgsConstructor
public class SmtpEmailService implements EmailService {

    /**
     * Spring component used to send emails via SMTP.
     */
    private final JavaMailSender mailSender;


    /**
     * Default sender email address.
     *
     * The value is injected from the application's configuration
     * property {@code spring.mail.username}.
     */
    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * EmailService implementation that sends plain-text emails using an SMTP server.
     *
     * @param recipientEmail recipient email address
     * @param subject email subject
     * @param body plain-text email body
     * @throws EmailDeliveryException if the email cannot be sent
     */
    @Override
    public void sendEmail(String recipientEmail, String subject, String body) {

        // Create a simple plain-text email message.
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Set the sender, recipient, subject, and body.
        mailMessage.setFrom(senderEmail);
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);

        try {
            // Send the email through the configured SMTP server.
            mailSender.send(mailMessage);
        } catch (MailException exception) {
            // Hide Spring Mail details behind an application-specific exception.
            throw new EmailDeliveryException("Failed to send the email.", exception);
        }
    }
}
