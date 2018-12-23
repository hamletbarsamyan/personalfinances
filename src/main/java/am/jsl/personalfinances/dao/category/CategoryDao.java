package am.jsl.personalfinances.dao.category;


import am.jsl.personalfinances.dao.BaseDao;
import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.dto.CategoryDTO;

import java.util.List;

/**
 * The Dao interface for accessing {@link Category} domain object.
 * @author hamlet
 */
public interface CategoryDao extends BaseDao<Category> {
    /**
     * Returns categories for the given user id.
     * @param userId the user id
     * @return the categories
     */
    List<CategoryDTO> getCategories(long userId);

    /**
     * Returns parent categories for the given user id.
     * @param userId the user id
     * @return the categories
     */
    List<CategoryDTO> lookupParentCategories(long userId);
}
