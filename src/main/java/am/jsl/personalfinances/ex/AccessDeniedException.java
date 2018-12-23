package am.jsl.personalfinances.ex;

/**
 * Will be thrown when a operation is denied based on users permission.
 * @author hamlet
 */
public class AccessDeniedException extends RuntimeException {
	private static final long serialVersionUID = -8821813744964271624L;
	public static final String CODE = "error.access_denied";

	public AccessDeniedException() {
		super();
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(Throwable cause) {
		super(cause);
	}

	public String getMessageCode() {
		return CODE;
	}
}
