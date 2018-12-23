package am.jsl.personalfinances.dto.event;

import java.io.Serializable;
import java.util.Date;

/**
 * The EventListDTO is used for transforming event data to web.
 *
 * @author hamlet
 */
public class EventListDTO implements Serializable {

	/**
	 * The internal identifier.
	 */
	private long id;

	/**
	 * The event type
	 */
	private String eventType;

	/**
	 * The message
	 */
	private String message;

	/**
	 * The user id
	 */
	private String performedBy;

	/**
	 * Target object id
	 */
	private String performedOn;

	/**
	 * The creation date
	 */
	private Date createdAt = null;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Sets event type.
	 *
	 * @param eventType the event type
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	/**
	 * Gets performed by.
	 *
	 * @return the performed by
	 */
	public String getPerformedBy() {
		return performedBy;
	}

	/**
	 * Sets performed by.
	 *
	 * @param performedBy the performed by
	 */
	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}

	/**
	 * Gets performed on.
	 *
	 * @return the performed on
	 */
	public String getPerformedOn() {
		return performedOn;
	}

	/**
	 * Sets performed on.
	 *
	 * @param performedOn the performed on
	 */
	public void setPerformedOn(String performedOn) {
		this.performedOn = performedOn;
	}

	/**
	 * Gets created at.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets created at.
	 *
	 * @param createdAt the created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
