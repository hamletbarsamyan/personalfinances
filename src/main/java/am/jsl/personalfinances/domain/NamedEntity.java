package am.jsl.personalfinances.domain;

import java.util.Objects;

/**
 * Domain object that extends BaseEntity and contains name field.
 * Used as a base class for objects needing these properties.
 *
 * @author hamlet
 */
public class NamedEntity extends BaseEntity {

    /**
     * The name
     */
    private String name;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof NamedEntity) {
            final NamedEntity other = (NamedEntity) o;
            return Objects.equals(getId(), other.getId())
                    && Objects.equals(getName(), other.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

}
