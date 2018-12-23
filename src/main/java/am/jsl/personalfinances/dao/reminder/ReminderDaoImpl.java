package am.jsl.personalfinances.dao.reminder;

import am.jsl.personalfinances.dao.BaseDaoImpl;
import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.dao.reminder.mapper.*;
import am.jsl.personalfinances.domain.reminder.Reminder;
import am.jsl.personalfinances.domain.reminder.ReminderStatus;
import am.jsl.personalfinances.domain.reminder.ReminderTransfer;
import am.jsl.personalfinances.domain.user.UserStatus;
import am.jsl.personalfinances.dto.reminder.ReminderDetailsDTO;
import am.jsl.personalfinances.dto.reminder.ReminderListDTO;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.search.reminder.ReminderSearchQuery;
import am.jsl.personalfinances.util.DateUtils;
import am.jsl.personalfinances.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Reminder} domain object.
 * @author hamlet
 */
@Repository("reminderDao")
@Lazy
public class ReminderDaoImpl extends BaseDaoImpl<Reminder> implements ReminderDao {
    private ReminderMapper reminderMapper = new ReminderMapper();
    private ReminderListDTOMapper listDTOMapper = new ReminderListDTOMapper();
    private ReminderDetailsMapper detailsMapper = new ReminderDetailsMapper();
    private ReminderLookupMapper lookupMapper = new ReminderLookupMapper();
    private ReminderTransferMapper reminderTransferMapper = new ReminderTransferMapper();

    private static final Map<String, String> sortByColumnMap = new HashMap<>();

    static {
        sortByColumnMap.put("type", "r.transaction_type AAA, r.id ");
        sortByColumnMap.put("dueDate", "r.due_date AAA, r.id ");
        sortByColumnMap.put("cat", "pcat.name, cat.name AAA, r.id ");
        sortByColumnMap.put("amount", "r.amount AAA, r.id ");
    }

    @Autowired
    public ReminderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    private static final String searchCommonSql = "select {columns} "
            + "from reminder r "
            + "inner join account acc on acc.id = r.account_id "
            + "inner join category cat on cat.id = r.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "where 1=1 ";
    private static final String columnsSql = "distinct r.id, r.status, acc.symbol," +
            "cat.name as category, cat.icon as category_icon, cat.color as category_color, " +
            "pcat.name as parent_category, pcat.icon as parent_category_icon, pcat.color as parent_category_color, " +
            "r.amount, r.transaction_type, r.due_date ";

    @Override
    public ListPaginatedResult<ReminderListDTO> search(ReminderSearchQuery searchQuery) {

        int rowsPerPage = searchQuery.getPageSize();
        int pageNum = searchQuery.getPage();
        int offset = (pageNum - 1) * rowsPerPage;

        ListPaginatedResult<ReminderListDTO> pagingResult = new ListPaginatedResult<>();
        Map<String, Object> params = new HashMap<>();
        String whereClause = createWhereClouse(searchQuery, params);

        String searchSql = searchCommonSql;
        searchSql = searchSql.replace("{columns}", columnsSql);

        StringBuilder limit = new StringBuilder();
        limit.append(" limit ").append(offset);
        limit.append(", ").append(rowsPerPage);

        StringBuilder finalSql = new StringBuilder();
        finalSql.append(searchSql);
        finalSql.append(whereClause);
        finalSql.append(createOrderByClause(searchQuery));
        finalSql.append(limit.toString());

        // search
        List<ReminderListDTO> list = parameterJdbcTemplate.query(
                finalSql.toString(), params, listDTOMapper);

        searchSql = searchCommonSql;
        searchSql = searchSql.replace("{columns}", "count(r.id) as cnt");

        finalSql = new StringBuilder();
        finalSql.append(searchSql);
        finalSql.append(whereClause);

        // count
        long count = parameterJdbcTemplate.queryForObject(finalSql.toString(),
                params, Long.class);

        pagingResult.setTotal(count);
        pagingResult.setList(list);

        return pagingResult;
    }

    private String createWhereClouse(ReminderSearchQuery searchQuery,
                                     Map<String, Object> params) {
        StringBuilder where = new StringBuilder();

        where.append(" and r.user_id = :userId");
        params.put("userId", searchQuery.getUserId());

        if (searchQuery.getId() != 0) {
            where.append(" and r.id = :id");
            params.put("id", searchQuery.getId());
            return where.toString();
        }

        if (searchQuery.getAccountId() != 0) {
            where.append(" and r.account_id = :account_id");
            params.put("account_id", searchQuery.getAccountId());
        }

        if (searchQuery.getCategoryId() != 0) {
            where.append(" and (r.category_id = :category_id or cat.parent_id = :category_id) ");
            params.put("category_id", searchQuery.getCategoryId());
        }

        if (searchQuery.getTransactionType() != 0) {
            where.append(" and r.transaction_type = :transactionType");
            params.put("transactionType", searchQuery.getTransactionType());
        }

        if (searchQuery.getStartDate() != null) {
            where.append(" and DATE(r.due_date) >= :startDate");
            params.put("startDate", searchQuery.getStartDate());
        }
        if (searchQuery.getEndDate() != null) {
            where.append(" and DATE(r.due_date) <= :endDate");
            params.put("endDate", searchQuery.getEndDate());
        }

        if (searchQuery.getContact() != 0) {
            where.append(" and r.contact_id = :contact_id");
            params.put("contact_id", searchQuery.getContact());
        }

        if (searchQuery.getStatus() != 0) {
            where.append(" and r.status = = :status");
            params.put("status", searchQuery.getStatus());
        }

        return where.toString();
    }

    private String createOrderByClause(ReminderSearchQuery searchQuery) {
        String sortBy = searchQuery.getSortBy();
        String result = "";

        if (TextUtils.hasText(sortBy)) {
            String sortByCol = sortByColumnMap.get(sortBy);

            if (TextUtils.hasText(sortByCol)) {
                result += " order by " + sortByCol;

                if (searchQuery.isAsc()) {
                    result = result.replaceAll("AAA", "");
                } else {
                    result = result.replaceAll("AAA", "desc");
                }
            }
        }

        return result;
    }

    private static final String deleteSql = "delete from reminder where user_id = :user_id and id = :id";

    @Override
    public void delete(long id, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        delete(id, userId, deleteSql);
        deleteReminderTransfer(id);
    }

    private static final String deleteReminderTransferSql = "delete from reminder_transfer where reminder_id = :reminder_id";

    private void deleteReminderTransfer(long reminderId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.reminder_id, reminderId);
        parameterJdbcTemplate.update(deleteReminderTransferSql, params);
    }

    private static final String createSql = "insert into reminder(id, status, due_date," +
            "account_id, category_id, contact_id, amount, transaction_type, auto_charge, reminder_repeat, description, user_id) "
            + "values(:id, :status, :due_date, :account_id, :category_id, :contact_id, :amount,  " +
            ":transaction_type, :auto_charge, :reminder_repeat, :description, :user_id)";

    @Override
    public void create(Reminder reminder) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "reminder");
        reminder.setId(id);
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, reminder.getId());
        params.put(DBUtils.status, reminder.getStatus());
        params.put(DBUtils.due_date, DateUtils.convert(reminder.getDueDate()));
        params.put(DBUtils.account_id, reminder.getAccountId());
        params.put(DBUtils.category_id, reminder.getCategoryId());
        params.put(DBUtils.contact_id, reminder.getContactId());
        params.put(DBUtils.amount, reminder.getAmount());
        params.put(DBUtils.transaction_type, reminder.getTransactionType());

        params.put(DBUtils.auto_charge, reminder.isAutoCharge());
        params.put(DBUtils.reminder_repeat, reminder.getRepeat());
        params.put(DBUtils.user_id, reminder.getUserId());
        params.put(DBUtils.description, reminder.getDescription());

        parameterJdbcTemplate.update(createSql, params);

        // create transfer details
        if (reminder.getReminderTransfer() != null) {
            createReminderTransfer(id, reminder.getReminderTransfer());
        }
    }

    private static final String createReminderTransferSql = "insert into reminder_transfer "
            + "(id, reminder_id, target_account_id, rate, converted_amount) "
            + "values (:id, :reminder_id, :target_account_id, :rate, :converted_amount)";

    public void createReminderTransfer(long reminderId, ReminderTransfer reminderTransfer) {
        long id = DBUtils.getNextId(getJdbcTemplate(), "reminder_transfer");
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.reminder_id, reminderId);
        params.put(DBUtils.target_account_id, reminderTransfer.getTargetAccountId());
        params.put(DBUtils.rate, reminderTransfer.getRate());
        params.put(DBUtils.converted_amount, reminderTransfer.getConvertedAmount());
        parameterJdbcTemplate.update(createReminderTransferSql, params);
    }

    private static final String getDetailsSql = "select distinct r.id, r.status, r.amount, "
            + "r.transaction_type, r.due_date, r.auto_charge, r.reminder_repeat, "
            + "acc.name as account, acc.icon as account_icon, acc.color as account_color, acc.symbol, "
            + "cat.name as category, cat.icon as category_icon, cat.color as category_color, "
            + "r.description, rt.target_account_id, rt.rate, rt.converted_amount, ct.name as contact "
            + "from reminder r "
            + "inner join account acc on acc.id = r.account_id "
            + "inner join category cat on cat.id = r.category_id "
            + "left join contact ct on ct.id = r.contact_id "
            + "left join reminder_transfer rt on rt.reminder_id = r.id "
            + "where r.id = :id and r.user_id = :user_id";

    @Override
    public ReminderDetailsDTO getDetails(long id, long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.user_id, userId);

        return parameterJdbcTemplate.queryForObject(getDetailsSql, params, detailsMapper);
    }

    private static final String dueRemindersSql = "select distinct r.id, r.reminder_repeat, r.auto_charge, acc.symbol, "
            + "cat.name as category, cat.icon as category_icon, cat.color as category_color, "
            + "pcat.name as parent_category, pcat.icon as parent_category_icon, pcat.color as parent_category_color, "
            + "r.amount, r.transaction_type, r.due_date, r.user_id "
            + "from reminder r "
            + "inner join pf_user u on u.id = r.user_id "
            + "inner join account acc on acc.id = r.account_id "
            + "inner join category cat on cat.id = r.category_id "
            + "left join category pcat on pcat.id = cat.parent_id "
            + "where u.enabled = :enabled and r.status = :status and DATE(r.due_date) <= :due_date ";

    @Override
    public List<ReminderListDTO> getDueReminders(long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.enabled, UserStatus.ENABLED);
        params.put(DBUtils.status, ReminderStatus.ACTIVE.getValue());
        params.put(DBUtils.due_date, DateUtils.convert(LocalDateTime.now()));

        StringBuilder sql = new StringBuilder(dueRemindersSql);

        if (userId != 0) {
            params.put(DBUtils.user_id, userId);
            sql.append(" and r.user_id = :user_id");
        }
        sql.append(" order by r.due_date asc");

        return parameterJdbcTemplate.query(sql.toString(), params, lookupMapper);
    }

    private static final String updateReminderDueSql = "update reminder "
            + "set status = :status, due_date = :due_date where id = :id";

    @Override
    public void updateReminderDue(long id, LocalDateTime dueDate) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, id);
        params.put(DBUtils.status, ReminderStatus.DONE.getValue());
        params.put(DBUtils.due_date, DateUtils.convert(dueDate));
        parameterJdbcTemplate.update(updateReminderDueSql, params);
    }

    private static final String createReminderTransactionSql = "insert into reminder_transaction "
            + "(reminder_id, transaction_id) values(:reminder_id, :transaction_id)";

    @Override
    public void createReminderTransaction(long reminderId, long transactionId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.reminder_id, reminderId);
        params.put(DBUtils.transaction_id, transactionId);
        parameterJdbcTemplate.update(createReminderTransactionSql, params);
    }

    private static final String updateSql = "update reminder "
            + "set status = :status, due_date = :due_date, "
            + "account_id = :account_id, category_id = :category_id, contact_id = :contact_id, amount = :amount, "
            + "transaction_type = :transaction_type, auto_charge = :auto_charge, "
            + "reminder_repeat = :reminder_repeat, description = :description "
            + "where user_id = :user_id and id = :id";

    @Override
    public void update(Reminder reminder) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.id, reminder.getId());
        params.put(DBUtils.status, reminder.getStatus());
        params.put(DBUtils.due_date, DateUtils.convert(reminder.getDueDate()));

        params.put(DBUtils.account_id, reminder.getAccountId());
        params.put(DBUtils.category_id, reminder.getCategoryId());
        params.put(DBUtils.contact_id, reminder.getContactId());
        params.put(DBUtils.amount, reminder.getAmount());
        params.put(DBUtils.transaction_type, reminder.getTransactionType());

        params.put(DBUtils.auto_charge, reminder.isAutoCharge());
        params.put(DBUtils.reminder_repeat, reminder.getRepeat());

        params.put(DBUtils.user_id, reminder.getUserId());
        params.put(DBUtils.description, reminder.getDescription());

        deleteReminderTransfer(reminder.getId());

        // recreate transfer details
        if (reminder.isTransfer()) {
            createReminderTransfer(reminder.getId(), reminder.getReminderTransfer());
        }

        parameterJdbcTemplate.update(updateSql, params);
    }

    private static final String getSql = "select * from reminder where user_id = :user_id and id = :id";

    @Override
    public Reminder get(long id, long userId) {
        Reminder reminder = get(id, userId, getSql, reminderMapper);

        if (reminder != null && reminder.isTransfer()) {
            reminder.setReminderTransfer(getReminderTransfer(id));
        }

        return reminder;
    }

    private static final String getReminderTransferSql = "select * from reminder_transfer "
            + "where reminder_id = :reminder_id";

    private ReminderTransfer getReminderTransfer(long reminderId) {
        Map<String, Object> params = new HashMap<>();
        params.put(DBUtils.reminder_id, reminderId);

        return parameterJdbcTemplate.queryForObject(getReminderTransferSql, params, reminderTransferMapper);
    }
}
