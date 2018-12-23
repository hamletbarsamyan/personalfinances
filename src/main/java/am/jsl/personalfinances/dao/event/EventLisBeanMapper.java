package am.jsl.personalfinances.dao.event;

import am.jsl.personalfinances.dao.DBUtils;
import am.jsl.personalfinances.domain.event.EventType;
import am.jsl.personalfinances.dto.event.EventListDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link RowMapper} implementation that converts a row into a new EventListDTO instance.
 * @author hamlet
 */
public class EventLisBeanMapper implements RowMapper<EventListDTO> {
	
	@Override
	public EventListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		EventListDTO event = new EventListDTO();
		event.setId(rs.getLong(DBUtils.id));
		byte eventTypeValue = rs.getByte(EventMapper.EVENT_TYPE);
		EventType eventType = EventType.getByValue(eventTypeValue);
		
		if (eventType != null) {
			event.setEventType(eventType.getOperation());
		}
		
		event.setMessage(rs.getString(EventMapper.MESSAGE));
		event.setPerformedBy(rs.getString(EventMapper.PERFORMED_BY));
		event.setCreatedAt(rs.getTimestamp(EventMapper.CREATED_AT));
		return event;
	}

}
