package am.jsl.personalfinances.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains category information.
 * @author hamlet
 */
public class CategoryDTO extends DescriptiveDTO implements Serializable {
    /**
     * The icon of category.
     */
    private String icon;

    /**
     * The color of category.
     */
    private String color;

    /**
     * The parent id of category.
     */
    private long parentId;

    /**
     * The child categories.
     */
    private List<CategoryDTO> childs;

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

    /**
     * Getter for property 'childs'.
     *
     * @return Value for property 'childs'.
     */
    public List<CategoryDTO> getChilds() {
        return childs;
    }

    /**
     * Setter for property 'childs'.
     *
     * @param childs Value to set for property 'childs'.
     */
    public void setChilds(List<CategoryDTO> childs) {
        this.childs = childs;
    }

    public void addChild(CategoryDTO category) {
        if (childs == null) {
            childs = new ArrayList<>();
        }
        childs.add(category);
    }
}
