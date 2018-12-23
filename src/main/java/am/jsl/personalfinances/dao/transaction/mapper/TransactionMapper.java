package am.jsl.personalfinances.dao.transaction.mapper;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.transaction.Transaction;
import am.jsl.personalfinances.util.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Transaction instance.
 * @author hamlet
 */
public class TransactionMapper implements RowMapper<Transaction> {

	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setId(rs.getLong(DBUtils.id));
		transaction.setAccountId(rs.getLong(DBUtils.account_id));
		transaction.setCategoryId(rs.getLong(DBUtils.category_id));
		transaction.setContactId(rs.getLong(DBUtils.contact_id));
		transaction.setAmount(rs.getDouble(DBUtils.amount));
		transaction.setStatus(rs.getByte(DBUtils.status));
		transaction.setTransactionType(rs.getByte(DBUtils.transaction_type));
		transaction.setTransactionDate(DateUtils.convert(rs.getTimestamp(DBUtils.transaction_date)));
		transaction.setDescription(rs.getString(DBUtils.description));
		transaction.setUserId(rs.getLong(DBUtils.user_id));
		return transaction;
	}
}
