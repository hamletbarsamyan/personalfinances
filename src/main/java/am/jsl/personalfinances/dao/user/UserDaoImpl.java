package am.jsl.personalfinances.dao.user;

import am.jsl.personalfinances.dao.BaseDaoImpl;
import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.user.Role;
import am.jsl.personalfinances.domain.user.User;
import am.jsl.personalfinances.domain.user.VerificationToken;
import am.jsl.personalfinances.domain.user.VerificationTokenType;
import am.jsl.personalfinances.ex.UserNotFoundException;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.user.UserSearchQuery;
import am.jsl.personalfinances.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link User} domain object.
 * @author hamlet
 */
@Repository("userDao")
@Lazy
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private VerificationTokenMapper tokenMapper = new VerificationTokenMapper();
    private UserMapper userMapper = new UserMapper();
    private RowMapper<User> userLoginMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong(DBUtils.id));
        user.setLogin(rs.getString(UserMapper.LOGIN));
        user.setPassword(rs.getString(UserMapper.PASSWORD));
        user.setFirstName(rs.getString(UserMapper.FIRST_NAME));
        user.setLastName(rs.getString(UserMapper.LAST_NAME));
        user.setEmail(rs.getString(DBUtils.email));
        user.setPhone(rs.getString(DBUtils.phone));
        user.setIcon(rs.getString(DBUtils.icon));
        user.setEnabled(rs.getBoolean(DBUtils.enabled));
        user.setRole(Role.valueOf(rs.getString(UserMapper.ROLE)));

        return user;
    };

    @Autowired
    UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String loginSql = "update pf_user set last_login = :last_login where id = :id";

    @Override
    public void login(long userId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DBUtils.id, userId);
        params.put(UserMapper.LAST_LOGIN,
                new Timestamp(System.currentTimeMillis()));
        parameterJdbcTemplate.update(loginSql, params);
    }

    private static final String deleteUserSql = "delete from pf_user where id = :id";

    @Override
    public void deleteUser(long id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DBUtils.id, id);
        parameterJdbcTemplate.update(deleteUserSql, params);
    }

    private static final String loginExistsSql = "select id from pf_user where LOWER(login) = ? and id != ?";

    @Override
    public boolean loginExists(String login, long id) {
        List<Long> list = getJdbcTemplate().queryForList(loginExistsSql,
                new Object[]{login.toLowerCase(), id}, Long.class);
        return list.size() > 0;
    }

    private static final String emailExistsSql = "select id from pf_user where email = ? and id != ?";

    @Override
    public boolean emailExists(String email, long id) {
        List<Long> list = getJdbcTemplate().queryForList(emailExistsSql,
                new Object[]{email, id}, Long.class);
        return list.size() > 0;
    }

    private static final String createUserSql = "insert into pf_user "
            + "(id, login, password, first_name, last_name, email, phone, "
            + "icon, enabled, role, created_at, changed_by, changed_at) "
            + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String getUsersSql = "select * from pf_user order by login";
    @Override
    public List<User> list(long userId) {
        return getJdbcTemplate().query(getUsersSql, userMapper);
    }

    @Override
    public boolean canDelete(long id, long userId) {
        return false;
    }

    @Override
    public void delete(long id, long userId) {

    }

    @Override
    public boolean exists(String name, long id, long userId) {
        return false;
    }

    @Override
    public void create(User user) {
        long userId = DBUtils.getNextId(getJdbcTemplate(), "pf_user");
        user.setId(userId);
        getJdbcTemplate().update(
                createUserSql,
                new Object[] { user.getId(), user.getLogin(),
                        user.getPassword(), user.getFirstName(), user.getLastName(),
                        user.getEmail(), user.getPhone(), user.getIcon(), user.isEnabled(),
                        user.getRole().name(), DateUtils.convert(user.getCreatedAt()),
                        user.getChangedBy(), DateUtils.convert(user.getChangedAt())});
    }

    private static final String updateUserSql = "update pf_user "
            + "set login = :login, first_name = :first_name, last_name = :last_name, email = :email, "
            + "email = :email, phone = :phone, icon = :icon, enabled = :enabled, role = :role, " +
            "changed_by = :changed_by, changed_at = :changed_at where id = :id";

    @Override
    public void update(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, user.getId());
        params.put(UserMapper.LOGIN, user.getLogin());
        params.put(UserMapper.FIRST_NAME, user.getFirstName());
        params.put(UserMapper.LAST_NAME, user.getLastName());
        params.put(DBUtils.email, user.getEmail());
        params.put(DBUtils.phone, user.getPhone());
        params.put(DBUtils.icon, user.getIcon());
        params.put(DBUtils.enabled, user.isEnabled());
        params.put(UserMapper.ROLE, user.getRole().name());
        params.put(DBUtils.changed_by, user.getChangedBy());
        params.put(DBUtils.changed_at, DateUtils.convert(user.getChangedAt()));
        parameterJdbcTemplate.update(updateUserSql, params);
    }

    @Override
    public User get(long id, long userId) {
        return null;
    }


    private static final String getUserSql = "select * from pf_user u where u.id = :id";

    @Override
    public User getUser(long userId) throws UserNotFoundException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DBUtils.id, userId);

        User user = parameterJdbcTemplate.queryForObject(
                getUserSql, params, userMapper);


        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    private static final String changePasswordSql = "update pf_user set changed_by = :changed_by, "
            + "changed_at = :changed_at, password = :password where id = :id";

    @Override
    public void changePassword(String encryptedPassword, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, userId);
        params.put(UserMapper.PASSWORD, encryptedPassword);
        params.put(DBUtils.changed_by, userId);
        params.put(DBUtils.changed_at,
                new Timestamp(System.currentTimeMillis()));
        params.put(UserMapper.LAST_PASSWORD_CHANGE, new Date());
        parameterJdbcTemplate.update(changePasswordSql, params);
    }

    private static final String getUserByNameSql = "select * from pf_user u where u.login = :login";

    @Override
    public User getUser(String username) throws UserNotFoundException {
        Map<String, String> params = new HashMap<>();
        params.put(UserMapper.LOGIN, username);

        List<User> users = parameterJdbcTemplate.query(
                getUserByNameSql, params, userLoginMapper);

        if (users.isEmpty()) {
            throw new UserNotFoundException("User [" + username + "] not found");
        } else {
            return users.get(0);
        }
    }

    private static final String updateIconSql = "update pf_user set icon = :icon where id = :id";
    @Override
    public void updateIcon(User user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DBUtils.id, user.getId());
        params.put(DBUtils.icon, user.getIcon());
        parameterJdbcTemplate.update(updateIconSql, params);
    }

    private static final String updateProfileSql = "update pf_user "
            + "set login = :login, first_name = :first_name, last_name = :last_name, email = :email, "
            + "email = :email, phone = :phone,  " +
            "changed_by = :changed_by, changed_at = :changed_at where id = :id";
    @Override
    public void updateProfile(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, user.getId());
        params.put(UserMapper.LOGIN, user.getLogin());
        params.put(UserMapper.FIRST_NAME, user.getFirstName());
        params.put(UserMapper.LAST_NAME, user.getLastName());
        params.put(DBUtils.email, user.getEmail());
        params.put(DBUtils.phone, user.getPhone());
        params.put(DBUtils.changed_by, user.getChangedBy());
        params.put(DBUtils.changed_at, DateUtils.convert(user.getChangedAt()));
        parameterJdbcTemplate.update(updateProfileSql, params);
    }

    private static final String getUserByEmailSql = "select * from pf_user u where u.email = :email";
    @Override
    public User getUserByEmail(String email) {
        Map<String, String> params = new HashMap<>();
        params.put(DBUtils.email, email);

        return parameterJdbcTemplate.queryForObject(getUserByEmailSql, params, userMapper);
    }

    private static final String searchSql = "select * from pf_user";
    private static final String countSql = "select count(id) from pf_user";
    @Override
    public ListPaginatedResult<User> search(UserSearchQuery userSearchQuery) {
        int rowsPerPage = userSearchQuery.getPageSize();
        int pageNum = userSearchQuery.getPage();
        int offset = (pageNum - 1) * rowsPerPage;

        ListPaginatedResult<User> result = new ListPaginatedResult<>();
        StringBuilder query = new StringBuilder(searchSql);
        query.append(" limit ").append(offset);
        query.append(", ").append(rowsPerPage);

        List<User> users = parameterJdbcTemplate.query(query.toString(), userMapper);
        result.setList(users);

        int total = getJdbcTemplate().queryForObject(countSql, Integer.class);
        result.setTotal(total);
        return result;
    }

    private static final String createVerificationTokenSql = "insert into verification_token "
            + "(id, user_id, token, token_type, expiry_date, expired) "
            + "values(:id, :user_id, :token, :token_type, :expiry_date, :expired)";
    @Override
    public void createVerificationToken(VerificationToken verificationToken) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "verification_token");
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, verificationToken.getUserId());
        params.put(DBUtils.token, verificationToken.getToken());
        params.put(DBUtils.token_type, verificationToken.getTokenType());
        params.put(DBUtils.expiry_date, verificationToken.getExpiryDate());
        params.put(DBUtils.expired, verificationToken.isExpired());
        parameterJdbcTemplate.update(createVerificationTokenSql, params);
    }

    private static final String updateVerificationTokenSql = "update verification_token "
            + "set token = :token, expiry_date = :expiry_date, expired = :expired where id = :id";
    @Override
    public void updateVerificationToken(VerificationToken verificationToken) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, verificationToken.getId());
        params.put(DBUtils.token, verificationToken.getToken());
        params.put(DBUtils.expiry_date, verificationToken.getExpiryDate());
        params.put(DBUtils.expired, verificationToken.isExpired());
        parameterJdbcTemplate.update(updateVerificationTokenSql, params);
    }

    private static final String getTokenSql = "select * from verification_token where user_id = :user_id and token_type = :token_type";
    @Override
    public VerificationToken getToken(long userId, VerificationTokenType tokenType) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.user_id, userId);
        params.put(DBUtils.token_type, tokenType.getValue());

        try {
            return parameterJdbcTemplate.queryForObject(getTokenSql, params, tokenMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
