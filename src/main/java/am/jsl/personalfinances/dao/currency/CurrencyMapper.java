package am.jsl.personalfinances.dao.currency;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Currency instance.
 * @author hamlet
 */
public class CurrencyMapper implements RowMapper<Currency> {

	public Currency mapRow(ResultSet rs, int rowNum) throws SQLException {
		Currency currency = new Currency();
		currency.setCode(rs.getString(DBUtils.iso_code));
		currency.setName(rs.getString(DBUtils.name));
		currency.setSymbol(rs.getString(DBUtils.symbol));
		return currency;
	}
}
