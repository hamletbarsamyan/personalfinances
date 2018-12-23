package am.jsl.personalfinances.dao.reminder.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.dto.reminder.ReminderDetailsDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new ReminderDetailsDTO instance.
 * @author hamlet
 */
public class ReminderDetailsMapper implements RowMapper<ReminderDetailsDTO> {

	public ReminderDetailsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReminderDetailsDTO dto = new ReminderDetailsDTO();
		dto.setId(rs.getLong(DBUtils.id));
		dto.setStatus(rs.getShort(DBUtils.status));
		dto.setDueDate(rs.getTimestamp(DBUtils.due_date));

		dto.setAccount(rs.getString(DBUtils.account));
		dto.setAccountIcon(rs.getString(DBUtils.account_icon));
		dto.setAccountColor(rs.getString(DBUtils.account_color));
		dto.setSymbol(rs.getString(DBUtils.symbol));

		dto.setCategory(rs.getString(DBUtils.category));
		dto.setCategoryIcon(rs.getString(DBUtils.category_icon));
		dto.setCategoryColor(rs.getString(DBUtils.category_color));
		dto.setContact(rs.getString(DBUtils.contact));
		dto.setAmount(rs.getDouble(DBUtils.amount));
		dto.setTransactionType(rs.getByte(DBUtils.transaction_type));

		dto.setAutoCharge(rs.getBoolean(DBUtils.auto_charge));
		dto.setRepeat(rs.getShort(DBUtils.reminder_repeat));
		dto.setDescription(rs.getString(DBUtils.description));
		dto.setContact(rs.getString(DBUtils.contact));

		dto.setTargetAccountId(rs.getLong(DBUtils.target_account_id));
		dto.setExchangeRate(rs.getDouble(DBUtils.rate));
		dto.setConvertedAmount(rs.getDouble(DBUtils.converted_amount));

		return dto;
	}
}
