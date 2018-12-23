package am.jsl.personalfinances.service.category;

import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.dto.CategoryDTO;
import am.jsl.personalfinances.service.BaseService;

import java.util.List;

/**
 * Service interface which defines all the methods for working with {@link Category} domain object.
 * @author hamlet
 */
public interface CategoryService extends BaseService<Category> {
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
