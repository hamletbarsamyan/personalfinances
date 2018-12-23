package am.jsl.personalfinances.service.event;

import am.jsl.personalfinances.domain.event.EventType;
import am.jsl.personalfinances.domain.user.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * A Singletone class for creating events.
 * @author hamlet
 */
@Component
public class EventLog implements Serializable, ApplicationContextAware {

    /**
     * The EventLog instance
     */
    private static EventLog log = null;

    /**
     * The ApplicationContext
     */
    private static ApplicationContext context;

    /**
     * the EventService
     */
    private EventService eventService;

    /**
     * Private constructor
     */
    private EventLog() {
    }

    /**
     * Returns the single instance of EventLog.
     * @return the EventLog
     */
    public static EventLog getInstance() {
        if (log == null) {
            log = new EventLog();
            EventService eventService = (EventService) context.getBean("eventService");
            log.setEventService(eventService);
        }
        return log;
    }

    /**
     * Sets the application context.
     * @param ac the ApplicationContext
     * @throws BeansException if thrown by application context methods
     */
    @Override
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        context = ac;
    }

    /**
     * Creates an event with the given options.
     * @param eventType the EventType
     * @param message the message
     * @param performedBy the user id
     */
    public void write(EventType eventType, String message, long performedBy) {
        eventService.saveEvent(eventType, message, performedBy);
    }

    /**
     * Creates an event with the given options.
     * @param eventType the EventType
     * @param message the message
     * @param user the user
     */
    public void write(EventType eventType, String message, User user) {
        eventService.saveEvent(eventType, message, user.getId());
    }

    /**
     * Creates an event with the given options.
     * @param eventType the EventType
     * @param performedBy the user id
     */
    public void write(EventType eventType, long performedBy) {
        eventService.saveEvent(eventType, "", performedBy);
    }

    /**
     * Creates an event with the given options.
     * @param eventType the EventType
     * @param user the user
     */
    public void write(EventType eventType, User user) {
        eventService.saveEvent(eventType, "", user.getId());
    }

    /**
     * Sets the EventService
     * @param eventService the EventService
     */
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }
}
