package am.jsl.personalfinances.ex;

/**
 * Will be thrown if an user login already exists during user registration.
 * @author hamlet
 */
public class DuplicateUserException extends Exception {

    public DuplicateUserException() {
    }

    public DuplicateUserException(String arg0) {
        super(arg0);
    }

    public DuplicateUserException(Throwable arg0) {
        super(arg0);
    }

    public DuplicateUserException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DuplicateUserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
