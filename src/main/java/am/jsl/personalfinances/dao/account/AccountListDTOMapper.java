package am.jsl.personalfinances.dao.account;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.dto.account.AccountListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new AccountListDTO instance.
 *
 * @author hamlet
 */
public class AccountListDTOMapper implements RowMapper<AccountListDTO> {

	public AccountListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		AccountListDTO account = new AccountListDTO();
		account.setId(rs.getLong(DBUtils.id));
		account.setName(rs.getString(DBUtils.name));
		account.setIcon(rs.getString(DBUtils.icon));
		account.setColor(rs.getString(DBUtils.color));
		account.setAccountType(rs.getByte(DBUtils.account_type));
		account.setBalance(rs.getDouble(DBUtils.balance));
		account.setCurrency(rs.getString(DBUtils.currency));
		account.setSymbol(rs.getString(DBUtils.symbol));
		account.setActive(rs.getBoolean(DBUtils.active));

		return account;
	}
}
