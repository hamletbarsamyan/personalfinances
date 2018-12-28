package am.jsl.personalfinances.service.event;

import am.jsl.personalfinances.domain.event.Event;
import am.jsl.personalfinances.domain.event.EventType;
import am.jsl.personalfinances.dto.event.EventListDTO;
import am.jsl.personalfinances.search.EventSearchQuery;
import am.jsl.personalfinances.search.ListPaginatedResult;
import am.jsl.personalfinances.service.BaseTest;
import am.jsl.personalfinances.util.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains EventService tests.
 */
public class EventServiceTest extends BaseTest {
    /**
     * The EventService
     */
    @Autowired
    protected EventService eventService;

    /**
     * Executed before all EventServiceTest tests.
     * @throws Exception if failed
     */
    @BeforeAll
    public void setUp() throws Exception {
        user = createUser();
    }

    @Test
    @DisplayName("Create Event Test")
    public void testCreateEvent() {
        log.info("Starting test for create event");
        Event event = new Event();
        event.setEventType(EventType.LOGIN.getValue());
        event.setMessage("Test event message");
        event.setPerformedBy(user.getId());
        eventService.saveEvent(event);
        assertTrue(event.getId() > 0);

        log.info("Finished test for create event");
    }

    @Test
    @DisplayName("Search Events Test")
    public void testSearchEvents() {
        log.info("Starting test for create event");
        Event event = new Event();
        event.setEventType(EventType.LOGIN.getValue());
        event.setMessage("Test event message");
        event.setPerformedBy(user.getId());
        eventService.saveEvent(event);

        EventSearchQuery searchQuery = new EventSearchQuery(1, Constants.PAGE_SIZE);
        searchQuery.setEventType(EventType.LOGIN.getValue());
        searchQuery.setPerformedBy(user.getId());
        ListPaginatedResult<EventListDTO> result = eventService.search(searchQuery);

        assertEquals(1, result.getTotal());

        log.info("Finished test for search events");
    }

    /**
     * Executed after all EventServiceTest tests.
     * @throws Exception if failed
     */
    @AfterAll
    public void cleanUp() throws Exception {
        super.cleanUp();
    }
}
