package am.jsl.personalfinances.dto.user;

import java.io.Serializable;

/**
 * The PasswordChangeDTO is used for changing password from user profile page.
 * @author hamlet
 */
public class PasswordChangeDTO implements Serializable {

    /**
     * The user id
     */
    private long id;

    /**
     * The old password
     */
    private String oldPassword;

    /**
     * The new password
     */
    private String newPassword;

    /**
     * The re entered new password
     */
    private String rePassword;

    /**
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for property 'oldPassword'.
     *
     * @return Value for property 'oldPassword'.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Setter for property 'oldPassword'.
     *
     * @param oldPassword Value to set for property 'oldPassword'.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
     * Getter for property 'rePassword'.
     *
     * @return Value for property 'rePassword'.
     */
    public String getRePassword() {
        return rePassword;
    }

    /**
     * Setter for property 'rePassword'.
     *
     * @param rePassword Value to set for property 'rePassword'.
     */
    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
