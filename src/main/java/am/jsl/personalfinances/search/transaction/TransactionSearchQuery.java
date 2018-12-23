package am.jsl.personalfinances.search.transaction;

import am.jsl.personalfinances.dto.transaction.TransactionListDTO;
import am.jsl.personalfinances.search.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Custom {@link Query} for searching of {@link TransactionListDTO} items.
 *
 * @author hamlet
 */
public class TransactionSearchQuery extends Query<TransactionListDTO> implements Serializable {
    private long accountId;
    private long categoryId;
    private short transactionType;
    private short transactionSource;
    private long contact;
    private Date startDate;
    private Date endDate;
    private long userId;
    private String description;

    private boolean calculateTotals = true;

    /**
     * Instantiates a new Transaction search query.
     *
     * @param page     the page
     * @param pageSize the page size
     */
    public TransactionSearchQuery(int page, int pageSize) {
        super(page, pageSize);
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
     * Gets transaction source.
     *
     * @return the transaction source
     */
    public short getTransactionSource() {
        return transactionSource;
    }

    /**
     * Sets transaction source.
     *
     * @param transactionSource the transaction source
     */
    public void setTransactionSource(short transactionSource) {
        this.transactionSource = transactionSource;
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

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Is calculate totals boolean.
     *
     * @return the boolean
     */
    public boolean isCalculateTotals() {
        return calculateTotals;
    }

    /**
     * Sets calculate totals.
     *
     * @param calculateTotals the calculate totals
     */
    public void setCalculateTotals(boolean calculateTotals) {
        this.calculateTotals = calculateTotals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, categoryId, transactionType, transactionSource, contact, startDate, endDate, userId, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TransactionSearchQuery other = (TransactionSearchQuery) obj;
        return Objects.equals(this.accountId, other.accountId)
                && Objects.equals(this.categoryId, other.categoryId)
                && Objects.equals(this.transactionType, other.transactionType)
                && Objects.equals(this.transactionSource, other.transactionSource)
                && Objects.equals(this.contact, other.contact)
                && Objects.equals(this.startDate, other.startDate)
                && Objects.equals(this.endDate, other.endDate)
                && Objects.equals(this.userId, other.userId)
                && Objects.equals(this.description, other.description);
    }
}
