package am.jsl.personalfinances.ex;

/**
 * Will be thrown if an user not found exists during user retrieve.
 * @author hamlet
 */
public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 2663040220470909688L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
