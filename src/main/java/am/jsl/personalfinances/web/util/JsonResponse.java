package am.jsl.personalfinances.web.util;

/**
 * The JsonResponse is used in ajax calls for sending responses.
 *
 * @author hamlet
 */
public class JsonResponse {
    /**
     * The flag indicating response status.
     */
    private boolean error = false;
    /**
     * The result object.
     */
    private Object result = null;

    /**
     * Is error boolean.
     *
     * @return the boolean
     */
    public boolean isError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public Object getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(Object result) {
        this.result = result;
    }
}