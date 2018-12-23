package am.jsl.personalfinances.dao;

import am.jsl.personalfinances.domain.NamedEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of BaseDao interface with Springframework's JDBC Dao support.
 * <p>Requires a {@link javax.sql.DataSource} to be set.
 *
 * @param <T> the type parameter
 * @author hamlet
 */
public class BaseDaoImpl<T> extends AbstractDaoImpl implements BaseDao<T> {

    /**
     * The row mapper for generic {@link NamedEntity} type.
     */
    protected NamedEntityMapper namedMapper = new NamedEntityMapper();

    /**
     * Creates a new BaseDaoImpl for the given {@link DataSource}.
     *
     * @param dataSource the JDBC DataSource to access
     */
    public BaseDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<T> list(long userId) {
        return null;
    }

    /**
     * List list.
     *
     * @param userId    the user id
     * @param sql       the sql
     * @param rowMapper the row mapper
     * @return the list
     */
    protected List<T> list(long userId, String sql, RowMapper<T> rowMapper) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public boolean canDelete(long id, long userId) {
        return true;
    }

    @Override
    public void delete(long id, long userId) {

    }

    @Override
    public boolean exists(String name, long id, long userId) {
        return false;
    }

    @Override
    public void create(T object) {

    }

    @Override
    public void update(T object) {

    }

    @Override
    public T get(long id, long userId) {
        return null;
    }

    @Override
    public List<T> lookup(long userId) {
        return null;
    }

    /**
     * Retrieves an entity by its id, user id and sql.
     * @param id     the entity id
     * @param userId the user id associated with the entity
     * @param sql    the sql for querying the entity
     * @param mapper the mapper for mapping entity from a {@link java.sql.ResultSet}
     * @return the entity
     */
    protected T get(long id, long userId, String sql, RowMapper<T> mapper) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);

        try {
            return parameterJdbcTemplate.queryForObject(sql, params, mapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Returns all instances of the NamedEntity for the given user.
     * @param userId the user id associated with the entity
     * @param sql    the sql for querying entities
     * @return the list
     */
    protected List<NamedEntity> lookup(long userId, String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(sql, params, namedMapper);
    }

    /**
     * Returns all instances of the type for the given user.
     * @param userId    the user id associated with the entity
     * @param sql       the sql for querying entities
     * @param rowMapper the mapper for mapping entities from a {@link java.sql.ResultSet}
     * @return the list
     */
    protected List<T> lookup(long userId, String sql, RowMapper<T> rowMapper) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.query(sql, params, rowMapper);
    }

    /**
     * Returns a string from the given entity.
     * @param id     the entity id
     * @param userId the user id associated with the entity
     * @param sql    the sql for querying string
     * @return the string
     */
    protected String getString(long id, long userId, String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.queryForObject(sql, params, String.class);
    }

    /**
     * Deletes the entity with the given id and user id.
     * @param id     the entity id
     * @param userId the user id associated with the entity
     * @param sql    the sql for deleting the entity
     */
    protected void delete(long id, long userId, String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);
        parameterJdbcTemplate.update(sql, params);
    }

    /**
     * Returns whether string exists in entity with the given id and user id exists.
     * @param name   the entity name
     * @param id     the entity id
     * @param userId the user id associated with the entity
     * @param sql    the sql for querying the entity
     * @return the boolean
     */
    protected boolean exists(String name, long id, long userId, String sql) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.name, name);
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);
        List<Long> list = parameterJdbcTemplate.queryForList(sql, params, Long.class);

        return list.size() > 0;
    }

    /**
     * Returns whether an entity with the given id and user id can be deleted.
     *
     * @param id     the user id associated with the entity
     * @param userId the user id
     * @param query  the sql for querying the entity
     * @return the boolean
     */
    protected boolean canDelete(long id, long userId, String query) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);

        List<Long> list = parameterJdbcTemplate.queryForList(query, params, Long.class);

        if (list.size() > 0) {
            return false;
        }
        return true;
    }
}
