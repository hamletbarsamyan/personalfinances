package am.jsl.personalfinances.dao.account;


import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.util.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *{@link RowMapper} implementation that converts a row into a new Account instance.
 *
 * @author hamlet
 */
public class AccountMapper implements RowMapper<Account> {

	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setId(rs.getLong(DBUtils.id));
		account.setName(rs.getString(DBUtils.name));
		account.setDescription(rs.getString(DBUtils.description));
		account.setBalance(rs.getDouble(DBUtils.balance));
		account.setAccountType(rs.getByte(DBUtils.account_type));
		account.setCurrency(rs.getString(DBUtils.currency));
		account.setSymbol(rs.getString(DBUtils.symbol));
		account.setActive(rs.getBoolean(DBUtils.active));
		account.setSortOrder(rs.getInt(DBUtils.sort_order));
		account.setIcon(rs.getString(DBUtils.icon));
		account.setColor(rs.getString(DBUtils.color));
		account.setUserId(rs.getLong(DBUtils.user_id));
		account.setCreatedAt(DateUtils.convert(rs.getTimestamp(DBUtils.created_at)));
		return account;
	}
}
