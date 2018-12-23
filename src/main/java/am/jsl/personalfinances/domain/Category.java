package am.jsl.personalfinances.domain;


import java.io.Serializable;
import java.util.Objects;

/**
 * The category domain object.
 *
 * @author hamlet
 */
public class Category extends Descriptive implements Serializable {
    /**
     * The default color of category
     */
    public static final String DEFAULT_COLOR= "#a0a0a0";

    /**
     * The icon of this category
     */
    private String icon;

    /**
     * The color of this category
     */
    private String color;

    /**
     * The parent id of this category
     */
    private long parentId;

    /**
     * Getter for property 'icon'.
     *
     * @return Value for property 'icon'.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Setter for property 'icon'.
     *
     * @param icon Value to set for property 'icon'.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Getter for property 'color'.
     *
     * @return Value for property 'color'.
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter for property 'color'.
     *
     * @param color Value to set for property 'color'.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Getter for property 'parentId'.
     *
     * @return Value for property 'parentId'.
     */
    public long getParentId() {
        return parentId;
    }

    /**
     * Setter for property 'parentId'.
     *
     * @param parentId Value to set for property 'parentId'.
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;

        if (o instanceof Category) {
            final Category other = (Category) o;
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
