package am.jsl.personalfinances.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple wrapper for slf4j logging which will allow easy switch to another logging system.
 */
public final class AppLogger {

    /**
     * The real logger instance.
     */
    private final Logger log;

    /**
     * Constructs new AppLogger for the given class.
     * @param clazz the class
     */
    public AppLogger(Class clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    /**
     * Logs the given exception at the ERROR level with the given message.
     * @param message the message
     * @param exception the Exception
     */
    public void error(String message, Exception exception) {
        log.error(message, exception);
    }

    /**
     * Logs the given throwable at the ERROR level with the given message.
     * @param message the message
     * @param throwable the Throwable
     */
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    /**
     * Logs the given message at the ERROR level.
     * @param message  the message
     */
    public void error(String message) {
        log.error(message);
    }

    /**
     * Logs the given message at the DEBUG level.
     * @param message the message
     */
    public void debug(String message) {
        log.debug(message);
    }

    /**
     * Logs a message at the DEBUG level with the given format and argument.
     * @param format the format
     * @param argument the argument
     */
    public void debug(String format, Object argument) {
        log.debug(format, argument);
    }

    /**
     * Logs the given message at the INFO level.
     * @param message the message
     */
    public void info(String message) {
        log.info(message);
    }
}
