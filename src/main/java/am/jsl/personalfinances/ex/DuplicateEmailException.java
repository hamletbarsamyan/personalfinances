package am.jsl.personalfinances.ex;

/**
 * Will be thrown if an user email already exists during user registration.
 * @author hamlet
 */
public class DuplicateEmailException extends Exception {

    public DuplicateEmailException() {
    }

    public DuplicateEmailException(String arg0) {
        super(arg0);
    }

    public DuplicateEmailException(Throwable arg0) {
        super(arg0);
    }

    public DuplicateEmailException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public DuplicateEmailException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
