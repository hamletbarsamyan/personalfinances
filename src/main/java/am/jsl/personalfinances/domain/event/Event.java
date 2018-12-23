package am.jsl.personalfinances.domain.event;

import am.jsl.personalfinances.domain.BaseEntity;

import java.io.Serializable;

/**
 * The event domain object.
 *
 * @author hamlet
 */
public class Event extends BaseEntity implements Serializable {

	/**
	 * The type of event
	 * @see EventType
	 */
	private byte eventType;

	/**
	 * The event message
	 */
	private String message;

	/**
	 * The user id created by event
	 */
	private long performedBy;

	/**
	 *  Constructs new event with the given fields.
	 *
	 * @param eventType the event type
	 * @param message the event message
	 * @param performedBy the user id that created this message
	 */
	public Event(EventType eventType, String message, long performedBy) {
		super();
		this.eventType = eventType.getValue();
		this.message = message;
		this.performedBy = performedBy;
	}

	/**
	 * The default constructor
	 */
    public Event() {
        super();
    }


	/**
	 * Gets event type.
	 *
	 * @return the event type
	 */
	public byte getEventType() {
		return eventType;
	}

	public void setEventType(byte eventType) {
		this.eventType = eventType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getPerformedBy() {
		return performedBy;
	}

	public void setPerformedBy(long performedBy) {
		this.performedBy = performedBy;
	}
}
