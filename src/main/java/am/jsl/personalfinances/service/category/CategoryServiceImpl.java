package am.jsl.personalfinances.service.category;

import am.jsl.personalfinances.dao.category.CategoryDao;
import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.dto.CategoryDTO;
import am.jsl.personalfinances.ex.CannotDeleteException;
import am.jsl.personalfinances.service.BaseServiceImpl;
import am.jsl.personalfinances.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service implementation of the {@link CategoryService}.
 * @author hamlet
 */
@Service("categoryService")
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    /**
     * The template file where is stored a html representation of categories.
     */
    private static final String CATEGORY_LOOKUP_HTML = "category-lookup.html";

    /**
     * The category dao.
     */
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDTO> getCategories(long userId) {
        List<CategoryDTO> categories = categoryDao.getCategories(userId);
        List<CategoryDTO> result = new ArrayList<>();
        Map<Long, CategoryDTO> parentMap = new HashMap<>();

        categories.forEach(category -> {
            long parentId = category.getParentId();
            CategoryDTO parent = parentMap.get(parentId);
            if (parent != null) {
                parent.addChild(category);
            } else {
                parentMap.put(category.getId(), category);
                result.add(category);
            }
        });

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(long id, long userId) throws CannotDeleteException {
        super.delete(id, userId);
        publish(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void create(Category category) throws Exception {
        if (TextUtils.isEmpty(category.getColor())) {
            category.setColor(Category.DEFAULT_COLOR);
        }
        super.create(category);
        publish(category.getUserId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Category category) throws Exception {
        if (TextUtils.isEmpty(category.getColor())) {
            category.setColor(Category.DEFAULT_COLOR);
        }
        super.update(category);
        publish(category.getUserId());
    }

    /**
     * Generates a html representation of categories for the given user id.
     * @param userId the user id
     */
    private void publish(long userId) {
        if (!publishHtml) {
            log.info("Publish html is disabled");
            return;
        }

        List<CategoryDTO> categories = getCategories(userId);

        StringBuilder html = new StringBuilder();
        html.append("<option value='0'></option>");
        long id;
        String icon;
        String color;

        for (CategoryDTO category : categories) {
            id = category.getId();
            icon = category.getIcon();
            icon = icon == null ? "" : icon;
            color = category.getColor();
            color = color == null ? "" : color;

            html.append("<option value='").append(id).append("'>");
            html.append("<span class=\"categoryCircle\"").append(" style=\"background-color:").append(color).append("\">");
            html.append("<span>").append(icon).append("</span></span>").append("&nbsp;");
            html.append("<span>").append(category.getName());
            html.append("</span></option>");

            List<CategoryDTO> childs = category.getChilds();

            if (childs == null) {
                continue;
            }

            for (CategoryDTO child : childs) {
                id = child.getId();
                icon = child.getIcon();
                icon = icon == null ? "" : icon;

                html.append("<option value='").append(id).append("'>").append("&nbsp;&nbsp;&nbsp;");
                html.append("<span>").append(icon).append("</span>").append("&nbsp;");
                html.append("<span>").append(child.getName());
                html.append("</span></option>");
            }
        }

        publish(userId, CATEGORY_LOOKUP_HTML, html.toString());
    }

    @Override
    public List<CategoryDTO> lookupParentCategories(long userId) {
        return categoryDao.lookupParentCategories(userId);
    }

    /**
     * Setter for property 'categoryDao'.
     *
     * @param categoryDao Value to set for property 'categoryDao'.
     */
    @Autowired
    public void setCategoryDao(@Qualifier("categoryDao") CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
        setBaseDao(categoryDao);
    }
}
