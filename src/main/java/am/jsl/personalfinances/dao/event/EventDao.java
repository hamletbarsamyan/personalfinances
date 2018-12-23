package am.jsl.personalfinances.dao.event;

import am.jsl.personalfinances.domain.event.Event;
import am.jsl.personalfinances.dto.event.EventListDTO;
import am.jsl.personalfinances.search.EventSearchQuery;
import am.jsl.personalfinances.search.ListPaginatedResult;

/**
 * The Dao interface for accessing {@link Event} domain object.
 * @author hamlet
 */
public interface EventDao {
	/**
	 * Saves the given event.
	 * @param event the event
	 */
	void saveEvent(Event event);

	/**
	 * Retrieves paginated result for the given search query.
	 * @param searchQuery the {@link EventSearchQuery} containing query options
	 * @return the {@link ListPaginatedResult} result
	 */
	ListPaginatedResult<EventListDTO> search(EventSearchQuery searchQuery);
}
