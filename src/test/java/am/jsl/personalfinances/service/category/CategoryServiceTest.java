package am.jsl.personalfinances.service.category;

import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.dto.CategoryDTO;
import am.jsl.personalfinances.service.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contains CategoryService tests.
 */
public class CategoryServiceTest extends BaseTest {

    /**
     * Executed before all CategoryServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Category Test")
    public void testCreateCategory() throws Exception {
        log.info("Starting test for create category");
        Category category = new Category();
        category.setName("test category");
        category.setIcon("icon");
        category.setColor("color");
        category.setDescription("description");
        category.setParentId(0);

        category.setCreatedAt(LocalDateTime.now());
        category.setUserId(user.getId());
        categoryService.create(category);

        Assert.assertTrue(category.getId() > 0);
        log.info("Finished test for create category");
    }

    @Test
    @DisplayName("Update Category Test")
    public void testUpdateCategory() throws Exception {
        log.info("Starting test for update category");
        String categoryName = "name updated";
        String icon = "icon updated";
        String color = "#1111";
        String description = "description updated";
        long parentId = 1;

        Category category = createCategory();

        // update category
        category.setName(categoryName);
        category.setIcon(icon);
        category.setColor(color);
        category.setParentId(parentId);
        category.setDescription(description);

        categoryService.update(category);

        // validate category
        category = categoryService.get(category.getId(), category.getUserId());

        Assert.assertEquals(categoryName, category.getName());
        Assert.assertEquals(icon, category.getIcon());
        Assert.assertEquals(color, category.getColor());
        Assert.assertEquals(parentId, category.getParentId());
        Assert.assertEquals(description, category.getDescription());

        log.info("Finished test for update category");
    }

    @Test
    @DisplayName("Delete Category Test")
    public void testDeleteCategory() throws Exception {
        log.info("Starting test for delete category");

        Category category = createCategory();
        long categoryId = category.getId();
        long userId = category.getUserId();

        categoryService.delete(category.getId(), category.getUserId());

        // validate category
        category = categoryService.get(categoryId, userId);
        Assert.assertNull(category);

        log.info("Finished test for delete category");
    }

    @Test
    @DisplayName("Get Categories Test")
    public void testListCategories() throws Exception {
        log.info("Starting test for getCategories");
        Category category = new Category();
        category.setName("test category");
        category.setIcon("icon");
        category.setColor("color");
        category.setDescription("description");
        category.setParentId(0);

        category.setCreatedAt(LocalDateTime.now());
        category.setUserId(user.getId());
        categoryService.create(category);

        List<CategoryDTO> categories = categoryService.getCategories(user.getId());

        Assert.assertEquals(1, categories.size());
        log.info("Finished test for getCategories");
    }

    @Test
    @DisplayName("Lookup Categories Test")
    public void testLookupCategories() throws Exception {
        log.info("Starting test for lookup categories");
        Category category = new Category();
        category.setName("test category");
        category.setIcon("icon");
        category.setColor("color");
        category.setDescription("description");
        category.setParentId(0);

        category.setCreatedAt(LocalDateTime.now());
        category.setUserId(user.getId());
        categoryService.create(category);

        List<Category> categories = categoryService.lookup(user.getId());

        Assert.assertEquals(1, categories.size());
        log.info("Finished test for lookup categories");
    }

    /**
     * Executed after all CategoryServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
