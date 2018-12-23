package am.jsl.personalfinances.dao.reminder.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.reminder.ReminderTransfer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new ReminderTransfer instance.
 * @author hamlet
 */
public class ReminderTransferMapper implements RowMapper<ReminderTransfer> {

	public ReminderTransfer mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReminderTransfer reminderTransfer = new ReminderTransfer();
		reminderTransfer.setId(rs.getLong(DBUtils.id));
		reminderTransfer.setReminderId(rs.getLong(DBUtils.reminder_id));
		reminderTransfer.setTargetAccountId(rs.getLong(DBUtils.target_account_id));
		reminderTransfer.setRate(rs.getDouble(DBUtils.rate));
		reminderTransfer.setConvertedAmount(rs.getDouble(DBUtils.converted_amount));

		return reminderTransfer;
	}
}
