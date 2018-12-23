package am.jsl.personalfinances.dao.reminder.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.dto.reminder.ReminderListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new ReminderListDTO instance.
 * @author hamlet
 */
public class ReminderLookupMapper implements RowMapper<ReminderListDTO> {

	public ReminderListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReminderListDTO listDTO = new ReminderListDTO();
		listDTO.setId(rs.getLong(DBUtils.id));
		listDTO.setRepeat(rs.getByte(DBUtils.reminder_repeat));
		listDTO.setAutoCharge(rs.getBoolean(DBUtils.auto_charge));

		listDTO.setSymbol(rs.getString(DBUtils.symbol));
		listDTO.setCategory(rs.getString(DBUtils.category));
		listDTO.setCategoryIcon(rs.getString(DBUtils.category_icon));
		listDTO.setCategoryColor(rs.getString(DBUtils.category_color));

		listDTO.setParentCategory(rs.getString(DBUtils.parent_category));
		listDTO.setParentCategoryIcon(rs.getString(DBUtils.parent_category_icon));
		listDTO.setParentCategoryColor(rs.getString(DBUtils.parent_category_color));

		listDTO.setAmount(rs.getDouble(DBUtils.amount));
		listDTO.setTransactionType(rs.getByte(DBUtils.transaction_type));
		listDTO.setDueDate(rs.getTimestamp(DBUtils.due_date));
		listDTO.setUserId(rs.getLong(DBUtils.user_id));
		return listDTO;
	}
}
