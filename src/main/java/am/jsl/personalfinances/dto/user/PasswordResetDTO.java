package am.jsl.personalfinances.dto.user;

import java.io.Serializable;

/**
 * The PasswordResetDTO is used for resetting (via email) password from login page.
 * @author hamlet
 */
public class PasswordResetDTO implements Serializable {

    /**
     * The user id
     */
    private long userId;

    /**
     * The user login
     */
    private String login;

    /**
     * The verification token
     */
    private String token;

    /**
     * The new password
     */
    private String newPassword;

    /**
     * The reentered new password
     */
    private String reNewPassword;

    /**
     * Getter for property 'userId'.
     *
     * @return Value for property 'userId'.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for property 'userId'.
     *
     * @param userId Value to set for property 'userId'.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Getter for property 'token'.
     *
     * @return Value for property 'token'.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for property 'token'.
     *
     * @param token Value to set for property 'token'.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for property 'newPassword'.
     *
     * @return Value for property 'newPassword'.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Setter for property 'newPassword'.
     *
     * @param newPassword Value to set for property 'newPassword'.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Getter for property 'reNewPassword'.
     *
     * @return Value for property 'reNewPassword'.
     */
    public String getReNewPassword() {
        return reNewPassword;
    }

    /**
     * Setter for property 'reNewPassword'.
     *
     * @param reNewPassword Value to set for property 'reNewPassword'.
     */
    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }

    /**
     * Getter for property 'login'.
     *
     * @return Value for property 'login'.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter for property 'login'.
     *
     * @param login Value to set for property 'login'.
     */
    public void setLogin(String login) {
        this.login = login;
    }
}
