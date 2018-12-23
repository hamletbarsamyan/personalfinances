package am.jsl.personalfinances.domain;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain object with common properties.
 * Used as a base class for objects needing these properties.
 *
 * @author hamlet
 */
public class BaseEntity implements Serializable {

    /**
     * The internal identifier. See # {@link #getId()} see {@link #setId(long)}.
     */
    private long id;
    /**
     * The User id.
     */
    protected long userId;
    /**
     * The Created at.
     */
    protected LocalDateTime createdAt = null;
    /**
     * The Changed by.
     */
    protected long changedBy = 0;
    /**
     * The Changed at.
     */
    protected LocalDateTime changedAt = null;

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
     * Gets user id.
     *
     * @return the user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets changed by.
     *
     * @return the changed by
     */
    public long getChangedBy() {
        return changedBy;
    }

    /**
     * Sets changed by.
     *
     * @param changedBy the changed by
     */
    public void setChangedBy(long changedBy) {
        this.changedBy = changedBy;
    }

    /**
     * Gets changed at.
     *
     * @return the changed at
     */
    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    /**
     * Sets changed at.
     *
     * @param changedAt the changed at
     */
    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d)", this.getClass().getSimpleName(), this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null)
            return false;

        if (o instanceof BaseEntity) {
            final BaseEntity other = (BaseEntity) o;
            return Objects.equals(getId(), other.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
