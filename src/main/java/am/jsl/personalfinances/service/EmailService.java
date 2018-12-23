package am.jsl.personalfinances.service;

import javax.mail.MessagingException;
import java.util.Locale;

/**
 * Service interface which defines all the methods for sending the emails.
 *
 * @author hamlet
 */
public interface EmailService {
    /**
     * Send password reset email to the given recipientEmail with password reset link.
     *
     * @param recipientEmail    the recipient email
     * @param resetPasswordLink the reset password link
     * @param locale            the locale
     * @throws MessagingException if error occurs
     */
    void sendPasswordResetMail(String recipientEmail, String resetPasswordLink, Locale locale)
            throws MessagingException;

    /**
     * Send registration confirm email to the given recipientEmail with confirm link.
     *
     * @param recipientEmail          the recipient email
     * @param registrationConfirmLink the registration confirm link
     * @param locale                  the locale
     * @throws MessagingException if error occurs
     */
    void sendRegistrationMail(String recipientEmail, String registrationConfirmLink, Locale locale)
            throws MessagingException;

    /**
     * Sends a email with the given parameters.
     *
     * @param email     the email address
     * @param subject the email subject
     * @param emailText the email text
     * @throws MessagingException if error occurs
     */
    void sendEmail(String email, String subject, String emailText)
            throws MessagingException;
}
