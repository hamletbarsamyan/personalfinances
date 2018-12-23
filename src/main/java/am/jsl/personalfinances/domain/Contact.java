package am.jsl.personalfinances.domain;

import java.io.Serializable;

/**
 * The contact domain object.
 *
 * @author hamlet
 */
public class Contact extends Descriptive implements Serializable {

    /**
     * The full name of contact
     */
    private String fullName;

    /**
     * The email of contact
     */
    private String email;

    /**
     * The phone of contact
     */
    private String phone;

    /**
     * Getter for property 'fullName'.
     *
     * @return Value for property 'fullName'.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for property 'fullName'.
     *
     * @param fullName Value to set for property 'fullName'.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for property 'email'.
     *
     * @return Value for property 'email'.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for property 'email'.
     *
     * @param email Value to set for property 'email'.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for property 'phone'.
     *
     * @return Value for property 'phone'.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for property 'phone'.
     *
     * @param phone Value to set for property 'phone'.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
