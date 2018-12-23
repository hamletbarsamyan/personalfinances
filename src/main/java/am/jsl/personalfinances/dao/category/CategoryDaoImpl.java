package am.jsl.personalfinances.dao.category;


import am.jsl.personalfinances.dao.BaseDaoImpl;
import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.Category;
import am.jsl.personalfinances.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Category} domain object.
 * @author hamlet
 */
@Repository("categoryDao")
@Lazy
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {
    private CategoryMapper categoryMapper = new CategoryMapper();
    private CategoryDTOMapper categoryDTOMapper = new CategoryDTOMapper();

    private RowMapper<CategoryDTO> parentLookupMapper = (rs, rowNum) -> {
        CategoryDTO entity = new CategoryDTO();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setName(rs.getString(DBUtils.name));
        entity.setIcon(rs.getString(DBUtils.icon));
        return entity;
    };

    private RowMapper<Category> lookupMapper = (rs, rowNum) -> {
        Category entity = new Category();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setName(rs.getString(DBUtils.name));
        return entity;
    };

    @Autowired
    CategoryDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String getCategoriesSql = "select c.id, c.name, c.icon, c.color, c.parent_id " +
            "from category c " +
            "where c.user_id = :user_id order by c.parent_id, c.name";

    @Override
    public List<CategoryDTO> getCategories(long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(getCategoriesSql, params, categoryDTOMapper);
    }

    private static final String canDeleteSql = "select c.id  from category c " +
            "inner join transaction tr on tr.category_id = c.id " +
            "where c.id = :id and c.user_id = :user_id";
    @Override
    public boolean canDelete(long id, long userId) {
        return canDelete(id, userId, canDeleteSql);
    }

    private static final String deleteSql = "delete from category where user_id = :user_id and id = :id";
    @Override
    public void delete(long id, long userId) {
        delete(id, userId, deleteSql);
    }

    private static final String existsSql = "select id from category where user_id = :user_id and LOWER(name) = :name and id != :id";
    @Override
    public boolean exists(String name, long id, long userId) {
        return exists(name, id, userId, existsSql);
    }

    private static final String createSql = "insert into category "
            + "(id, name, icon, color, description, parent_id, user_id) "
            + "values(:id, :name, :icon, :color, :description, :parent_id, :user_id)";

    @Override
    public void create(Category category) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "category");
        category.setId(id);
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, category.getId());
        params.put(DBUtils.name, category.getName());
        params.put(DBUtils.icon, category.getIcon());
        params.put(DBUtils.color, category.getColor());
        params.put(DBUtils.description, category.getDescription());
        params.put(DBUtils.parent_id, category.getParentId());
        params.put(DBUtils.user_id, category.getUserId());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String updateSql = "update category "
            + "set name = :name, icon = :icon, color = :color, "
            + "description = :description, parent_id = :parent_id "
            + "where user_id = :user_id and id = :id";

    @Override
    public void update(Category category) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, category.getId());
        params.put(DBUtils.name, category.getName());
        params.put(DBUtils.icon, category.getIcon());
        params.put(DBUtils.color, category.getColor());
        params.put(DBUtils.description, category.getDescription());
        params.put(DBUtils.parent_id, category.getParentId());
        params.put(DBUtils.user_id, category.getUserId());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getSql = "select * from category where user_id = :user_id and id = :id";
    @Override
    public Category get(long id, long userId) {
        return get(id, userId, getSql, categoryMapper);
    }

    private static final String lookupParentCategoriesSql = "select id, name, icon from category where user_id = :user_id and parent_id = 0 " +
            "order by name asc";
    @Override
    public List<CategoryDTO> lookupParentCategories(long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(lookupParentCategoriesSql, params, parentLookupMapper);
    }

    private static final String lookupSql = "select id, name  " +
            "from category where user_id = :user_id order by name";
    @Override
    public List<Category> lookup(long userId) {
        return lookup(userId, lookupSql, lookupMapper);
    }
}
