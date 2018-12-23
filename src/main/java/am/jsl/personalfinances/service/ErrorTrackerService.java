package am.jsl.personalfinances.service;

/**
 * Service interface which defines a method for emailing exception details.
 *
 * @author hamlet
 */
public interface ErrorTrackerService {

    /**
     * Emails the exception details.
     * @param e the Exception
     */
    void sendError(final Exception e);
}
