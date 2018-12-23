package am.jsl.personalfinances.dto;

import java.io.Serializable;

/**
 * DTO object that extends BaseDTO and contains name field.
 * Used as a base class for objects needing these properties.
 * @author hamlet
 */
public class NamedDTO  extends BaseDTO implements Serializable {
    /**
     * The name.
     */
    private String name;

    /**
     * Getter for property 'name'.
     *
     * @return Value for property 'name'.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     *
     * @param name Value to set for property 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }
}
