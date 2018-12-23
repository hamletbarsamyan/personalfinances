package am.jsl.personalfinances.dao.reminder.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.reminder.Reminder;
import am.jsl.personalfinances.util.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Reminder instance.
 * @author hamlet
 */
public class ReminderMapper implements RowMapper<Reminder> {

	public Reminder mapRow(ResultSet rs, int rowNum) throws SQLException {
		Reminder reminder = new Reminder();
		reminder.setId(rs.getLong(DBUtils.id));
		reminder.setStatus(rs.getByte(DBUtils.status));
		reminder.setDueDate(DateUtils.convert(rs.getTimestamp(DBUtils.due_date)));

		reminder.setAccountId(rs.getLong(DBUtils.account_id));
		reminder.setCategoryId(rs.getLong(DBUtils.category_id));
		reminder.setContactId(rs.getLong(DBUtils.contact_id));
		reminder.setAmount(rs.getDouble(DBUtils.amount));
		reminder.setTransactionType(rs.getByte(DBUtils.transaction_type));

		reminder.setAutoCharge(rs.getBoolean(DBUtils.auto_charge));
		reminder.setRepeat(rs.getByte(DBUtils.reminder_repeat));
		reminder.setDescription(rs.getString(DBUtils.description));
		reminder.setUserId(rs.getLong(DBUtils.user_id));
		return reminder;
	}
}
