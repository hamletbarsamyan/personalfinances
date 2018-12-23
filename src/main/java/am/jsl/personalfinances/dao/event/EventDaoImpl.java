package am.jsl.personalfinances.dao.event;

import am.jsl.personalfinances.dao.AbstractDaoImpl;
import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.event.Event;
import am.jsl.personalfinances.dto.event.EventListDTO;
import am.jsl.personalfinances.search.EventSearchQuery;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The implementation of Dao interface for accessing {@link Event} domain object.
 * @author hamlet
 */
@Repository("eventDao")
@Lazy
public class EventDaoImpl extends AbstractDaoImpl implements EventDao {

	private EventLisBeanMapper eventListBeanMapper = new EventLisBeanMapper();
	
	private static final Map<String, String> sortByColumnMap = new HashMap<>();

	static {
		sortByColumnMap.put("eventType", "e.event_type AAA, e.id ");		
		sortByColumnMap.put("message", "e.message AAA, e.id ");		
		sortByColumnMap.put("performedBy", "u.login AAA, e.id ");		
		sortByColumnMap.put("createdAt", "e.created_at AAA, e.id ");
	}

	@Autowired
	EventDaoImpl(DataSource dataSource) {
		super(dataSource);
	}

	private static final String saveEventSql = "insert into event (id, event_type, message, performed_by, created_at) "
			+ " values(:id, :event_type, :message, :performed_by, sysdate())";

	@Override
	public void saveEvent(Event event) {
		long id = DBUtils.getNextId(getJdbcTemplate(), "event");
		event.setId(id);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DBUtils.id, id);
		params.put(EventMapper.EVENT_TYPE, event.getEventType());
		params.put(EventMapper.MESSAGE, event.getMessage());
		params.put(EventMapper.PERFORMED_BY, event.getPerformedBy());
		parameterJdbcTemplate.update(saveEventSql, params);
	}

	private static final String searchCommonSql = "select {columns} "
			+ "from event e " + "left join pf_user u on u.id = e.performed_by "
			+ "where 1=1 ";
	private static final String columnsSql = "distinct e.id, e.event_type, e.message, u.login as performed_by, e.created_at ";

	@Override
	public ListPaginatedResult<EventListDTO> search(EventSearchQuery searchQuery) {

		int rowsPerPage = searchQuery.getPageSize();
		int pageNum = searchQuery.getPage();
		int offset = (pageNum - 1) * rowsPerPage;
		String sortBy = "createdAt";
		boolean ascending = false;

		ListPaginatedResult<EventListDTO> pagingResult = new ListPaginatedResult<EventListDTO>();
		Map<String, Object> params = new HashMap<String, Object>();
		String whereClause = createtWhereClouse(searchQuery, params);

		String searchSql = searchCommonSql;
		searchSql = searchSql.replace("{columns}", columnsSql);

		StringBuilder limit = new StringBuilder();
		limit.append(" limit ").append(offset);
		limit.append(", ").append(rowsPerPage);

		StringBuilder finalSql = new StringBuilder();
		finalSql.append(searchSql);
		finalSql.append(whereClause);
		finalSql.append(createOrderByClause(sortBy, ascending));
		finalSql.append(limit.toString());

		List<EventListDTO> list = parameterJdbcTemplate.query(
				finalSql.toString(), params, eventListBeanMapper);

		searchSql = searchCommonSql;
		searchSql = searchSql.replace("{columns}", "count(e.id) as cnt");

		finalSql = new StringBuilder();
		finalSql.append(searchSql);
		finalSql.append(whereClause);

		long count = parameterJdbcTemplate.queryForObject(finalSql.toString(),
				params, Long.class);

		pagingResult.setTotal(count);
		pagingResult.setList(list);
		return pagingResult;
	}

	private String createtWhereClouse(EventSearchQuery searchQuery,
                                      Map<String, Object> params) {
		StringBuilder where = new StringBuilder();

		if (searchQuery.getEventType() != 0) {
			where.append(" and e.event_type = :event_type");
			params.put("event_type", searchQuery.getEventType());
		}

		if (searchQuery.getPerformedBy() != 0) {
			where.append(" and e.performed_by = :performed_by");
			params.put("performed_by", searchQuery.getPerformedBy());
		}

		if (searchQuery.getCreatedAtStart() != null) {
			where.append(" and e.created_at >= :createdAtStart");
			params.put("createdAtStart", searchQuery.getCreatedAtStart());
		}
		if (searchQuery.getCreatedAtEnd() != null) {
			where.append(" and e.created_at <= :createdAtEnd");
			params.put("createdAtEnd", searchQuery.getCreatedAtEnd());
		}

		String message = searchQuery.getMessage();

		if (TextUtils.hasText(message)) {
			message = message.replace("*", "%");
			where.append(" and e.message like :message");
			params.put("message", message);
		}
		return where.toString();
	}

	private String createOrderByClause(String sortBy, boolean ascending) {
		String result = "";

		if (TextUtils.hasText(sortBy)) {
			String sortByCol = sortByColumnMap.get(sortBy);

			if (TextUtils.hasText(sortByCol)) {
				result += " order by " + sortByCol;

				if (ascending) {
					result = result.replaceAll("AAA", "");
				} else {
					result = result.replaceAll("AAA", "desc");
				}
			}
		}

		return result;
	}
}
