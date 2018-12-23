package am.jsl.personalfinances.dao.contact;

import am.jsl.personalfinances.dao.BaseDaoImpl;
import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Contact} domain object.
 * @author hamlet
 */
@Repository("contactDao")
@Lazy
public class ContactDaoImpl extends BaseDaoImpl<Contact> implements ContactDao {
    private ContactMapper contactMapper = new ContactMapper();
    private RowMapper<Contact> lookupMapper = (rs, rowNum) -> {
        Contact entity = new Contact();
        entity.setId(rs.getLong(DBUtils.id));
        entity.setName(rs.getString(DBUtils.name));
        return entity;
    };

    @Autowired
    ContactDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String deleteSql = "delete from contact where user_id = :user_id and id = :id";
    @Override
    public void delete(long id, long userId) {
        delete(id, userId, deleteSql);
    }

    private static final String createSql = "insert into contact "
            + "(id, name, email, phone, description, user_id) "
            + "values(:id, :name, :email, :phone, :description, :user_id)";

    @Override
    public void create(Contact contact) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "contact");
        contact.setId(id);
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, contact.getId());
        params.put(DBUtils.name, contact.getName());
        params.put(DBUtils.phone, contact.getPhone());
        params.put(DBUtils.email, contact.getEmail());
        params.put(DBUtils.description, contact.getDescription());
        params.put(DBUtils.user_id, contact.getUserId());
        parameterJdbcTemplate.update(createSql, params);
    }

    private static final String updateSql = "update contact "
            + "set name = :name, email = :email, phone = :phone, "
            + "description = :description "
            + "where user_id = :user_id and id = :id";

    @Override
    public void update(Contact contact) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, contact.getId());
        params.put(DBUtils.name, contact.getName());
        params.put(DBUtils.phone, contact.getPhone());
        params.put(DBUtils.email, contact.getEmail());
        params.put(DBUtils.description, contact.getDescription());
        params.put(DBUtils.user_id, contact.getUserId());
        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getSql = "select * from contact where user_id = :user_id and id = :id";
    @Override
    public Contact get(long id, long userId) {
        return get(id, userId, getSql, contactMapper);
    }

    private static final String listSql = "select * from contact where user_id = :user_id order by name";
    @Override
    public List<Contact> list(long userId) {
        return list(userId, listSql, contactMapper);
    }

    private static final String lookupSql = "select id, name  " +
            "from contact where user_id = :user_id order by name";
    @Override
    public List<Contact> lookup(long userId) {
        return lookup(userId, lookupSql, lookupMapper);
    }

    private static final String getTitleSql = "select name  from contact where id = :id and user_id = :user_id";
    @Override
    public String getTitle(long id, long userId) {
        return getString(id, userId, getTitleSql);
    }
}
