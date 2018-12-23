package am.jsl.personalfinances.dao;

import am.jsl.personalfinances.domain.NamedEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new NamedEntity instance.
 * @author hamlet
 */
public class NamedEntityMapper implements RowMapper<NamedEntity> {

	@Override
	public NamedEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		NamedEntity namedEntity = new NamedEntity();
		namedEntity.setId(rs.getLong(DBUtils.id));
		namedEntity.setName(rs.getString(DBUtils.name));
		return namedEntity;
	}

}
