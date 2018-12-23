package am.jsl.personalfinances.service.event;

import am.jsl.personalfinances.domain.event.Event;
import am.jsl.personalfinances.domain.event.EventType;
import am.jsl.personalfinances.dto.event.EventListDTO;
import am.jsl.personalfinances.search.EventSearchQuery;
import am.jsl.personalfinances.search.ListPaginatedResult;

/**
 * Service interface which defines all the methods for working with {@link Event} domain object.
 * @author hamlet
 */
public interface EventService {
    /**
     * Saves the given event.
     * @param event the event
     */
    void saveEvent(Event event);

    /**
     * Creates an event with the given options.
     * @param eventType the event type
     * @param message the message
     * @param performedBy the user id
     */
    void saveEvent(EventType eventType, String message, long performedBy);

    /**
     * Retrieves paginated result for the given search query.
     * @param searchQuery the {@link EventSearchQuery} containing query options
     * @return the {@link ListPaginatedResult} result
     */
    ListPaginatedResult<EventListDTO> search(EventSearchQuery searchQuery);
}
