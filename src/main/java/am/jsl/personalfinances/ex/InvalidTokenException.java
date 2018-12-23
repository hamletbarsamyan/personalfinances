package am.jsl.personalfinances.ex;

/**
 * Will be thrown if user token is invalid or expired.
 * @author hamlet
 */
public class InvalidTokenException extends RuntimeException {
	public static final String CODE = "error.invalid.token";

	public InvalidTokenException() {
		super();
	}

	public InvalidTokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException(Throwable cause) {
		super(cause);
	}

	public String getMessageCode() {
		return CODE;
	}
}
