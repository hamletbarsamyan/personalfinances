package am.jsl.personalfinances.search.transaction;

import am.jsl.personalfinances.dto.transaction.TransactionByCategoryDTO;
import am.jsl.personalfinances.search.Query;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Custom {@link Query} for searching of {@link TransactionByCategoryDTO} items.
 * @author hamlet
 */
public class TransactionByCategorySearchQuery extends Query<TransactionByCategoryDTO> implements Serializable {
    private long accountId;
    private short transactionType;
    private Date startDate;
    private Date endDate;
    private long userId;

    /**
     * Getter for property 'accountId'.
     *
     * @return Value for property 'accountId'.
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Setter for property 'accountId'.
     *
     * @param accountId Value to set for property 'accountId'.
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    /**
     * Getter for property 'transactionType'.
     *
     * @return Value for property 'transactionType'.
     */
    public short getTransactionType() {
        return transactionType;
    }

    /**
     * Setter for property 'transactionType'.
     *
     * @param transactionType Value to set for property 'transactionType'.
     */
    public void setTransactionType(short transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Getter for property 'startDate'.
     *
     * @return Value for property 'startDate'.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter for property 'startDate'.
     *
     * @param startDate Value to set for property 'startDate'.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter for property 'endDate'.
     *
     * @return Value for property 'endDate'.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter for property 'endDate'.
     *
     * @param endDate Value to set for property 'endDate'.
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter for property 'userId'.
     *
     * @return Value for property 'userId'.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Setter for property 'userId'.
     *
     * @param userId Value to set for property 'userId'.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, transactionType, startDate, endDate, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TransactionByCategorySearchQuery other = (TransactionByCategorySearchQuery) obj;
        return Objects.equals(this.accountId, other.accountId)
                && Objects.equals(this.transactionType, other.transactionType)
                && Objects.equals(this.startDate, other.startDate)
                && Objects.equals(this.endDate, other.endDate)
                && Objects.equals(this.userId, other.userId);
    }
}
