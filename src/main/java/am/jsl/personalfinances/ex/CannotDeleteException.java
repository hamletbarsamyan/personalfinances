package am.jsl.personalfinances.ex;

/**
 * Will be thrown if an object cannot be deleted.
 * @author hamlet
 */
public class CannotDeleteException extends Exception {

    public CannotDeleteException() {
    }

    public CannotDeleteException(String message) {
        super(message);
    }

    public CannotDeleteException(Throwable cause) {
        super(cause);
    }

    public CannotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDeleteException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
