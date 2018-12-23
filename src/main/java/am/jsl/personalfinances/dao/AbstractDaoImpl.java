package am.jsl.personalfinances.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;

/**
 * Base class with Springframework's JDBC Dao support.
 * <p>Requires a {@link javax.sql.DataSource} to be set.
 * @author hamlet
 */
public class AbstractDaoImpl extends JdbcDaoSupport {

	/**
	 * Allowing the use of named parameters
	 */
	protected NamedParameterJdbcTemplate parameterJdbcTemplate;

	/**
	 * Creates a new AbstractDaoImpl for the given {@link DataSource}.
	 * @param dataSource the JDBC DataSource to access
	 */
	public AbstractDaoImpl(DataSource dataSource) {
		super();
		setDataSource(dataSource);
		parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}
