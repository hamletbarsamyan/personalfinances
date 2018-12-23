package am.jsl.personalfinances.search.reminder;

import am.jsl.personalfinances.dto.reminder.ReminderListDTO;
import am.jsl.personalfinances.search.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Custom {@link Query} for searching of {@link ReminderListDTO} items.
 *
 * @author hamlet
 */
public class ReminderSearchQuery extends Query<ReminderListDTO> implements Serializable {
    private short status;
    private long accountId;
    private long categoryId;
    private short transactionType;
    private long contact;
    private Date startDate;
    private Date endDate;
    private long userId;

    /**
     * Instantiates a new Reminder search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public ReminderSearchQuery(int page, int pageSize) {
        super(page, pageSize);
    }


    /**
     * Gets status.
     *
     * @return the status
     */
    public short getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(short status) {
        this.status = status;
    }

    /**
     * Gets account id.
     *
     * @return the account id
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Sets account id.
     *
     * @param accountId the account id
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets transaction type.
     *
     * @return the transaction type
     */
    public short getTransactionType() {
        return transactionType;
    }

    /**
     * Sets transaction type.
     *
     * @param transactionType the transaction type
     */
    public void setTransactionType(short transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets contact.
     *
     * @return the contact
     */
    public long getContact() {
        return contact;
    }

    /**
     * Sets contact.
     *
     * @param contact the contact
     */
    public void setContact(long contact) {
        this.contact = contact;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @Override
    public int hashCode() {
        return Objects.hash(status, accountId, categoryId, transactionType, contact, startDate, endDate, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ReminderSearchQuery other = (ReminderSearchQuery) obj;
        return  Objects.equals(this.status, other.status)
                && Objects.equals(this.accountId, other.accountId)
                && Objects.equals(this.categoryId, other.categoryId)
                && Objects.equals(this.transactionType, other.transactionType)
                && Objects.equals(this.contact, other.contact)
                && Objects.equals(this.startDate, other.startDate)
                && Objects.equals(this.endDate, other.endDate)
                && Objects.equals(this.userId, other.userId);
    }
}
