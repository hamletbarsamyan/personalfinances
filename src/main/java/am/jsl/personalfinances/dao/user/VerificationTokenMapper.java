package am.jsl.personalfinances.dao.user;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.user.VerificationToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new VerificationToken instance.
 * @author hamlet
 */
public class VerificationTokenMapper implements RowMapper<VerificationToken> {

	public VerificationToken mapRow(ResultSet rs, int rowNum) throws SQLException {
		VerificationToken token = new VerificationToken();
		token.setId(rs.getLong(DBUtils.id));
		token.setUserId(rs.getLong(DBUtils.user_id));
		token.setToken(rs.getString(DBUtils.token));
		token.setTokenType(rs.getByte(DBUtils.token_type));
		token.setExpiryDate(rs.getDate(DBUtils.expiry_date));
		token.setExpired(rs.getBoolean(DBUtils.expired));
		return token;
	}
}
