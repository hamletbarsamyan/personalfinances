package am.jsl.personalfinances.search;

import am.jsl.personalfinances.dto.event.EventListDTO;

import java.util.Date;

/**
 * Custom {@link Query} for searching of {@link EventListDTO} items.
 *
 * @author hamlet
 */
public class EventSearchQuery extends Query<EventListDTO> {
    private int eventType;
    private long performedBy;
    private Date createdAtStart = null;
    private Date createdAtEnd = null;
    private String message;

    /**
     * Instantiates a new Event search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public EventSearchQuery(int page, int pageSize) {
        super(page, pageSize);
    }


    /**
     * Gets event type.
     *
     * @return the event type
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * Sets event type.
     *
     * @param eventType the event type
     */
    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets performed by.
     *
     * @return the performed by
     */
    public long getPerformedBy() {
        return performedBy;
    }

    /**
     * Sets performed by.
     *
     * @param performedBy the performed by
     */
    public void setPerformedBy(long performedBy) {
        this.performedBy = performedBy;
    }

    /**
     * Gets created at start.
     *
     * @return the created at start
     */
    public Date getCreatedAtStart() {
        return createdAtStart;
    }

    /**
     * Sets created at start.
     *
     * @param createdAtStart the created at start
     */
    public void setCreatedAtStart(Date createdAtStart) {
        this.createdAtStart = createdAtStart;
    }

    /**
     * Gets created at end.
     *
     * @return the created at end
     */
    public Date getCreatedAtEnd() {
        return createdAtEnd;
    }

    /**
     * Sets created at end.
     *
     * @param createdAtEnd the created at end
     */
    public void setCreatedAtEnd(Date createdAtEnd) {
        this.createdAtEnd = createdAtEnd;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
