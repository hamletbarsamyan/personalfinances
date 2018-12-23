package am.jsl.personalfinances.dao.contact;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.Contact;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new Contact instance.
 * @author hamlet
 */
public class ContactMapper implements RowMapper<Contact> {

	public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contact contact = new Contact();
		contact.setId(rs.getLong(DBUtils.id));
		contact.setName(rs.getString(DBUtils.name));
		contact.setPhone(rs.getString(DBUtils.phone));
		contact.setEmail(rs.getString(DBUtils.email));
		contact.setDescription(rs.getString(DBUtils.description));
		contact.setUserId(rs.getLong(DBUtils.user_id));
		return contact;
	}
}
