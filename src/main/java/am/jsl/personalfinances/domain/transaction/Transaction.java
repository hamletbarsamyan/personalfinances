package am.jsl.personalfinances.domain.transaction;

import am.jsl.personalfinances.domain.Descriptive;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The transaction domain object.
 *
 * @author hamlet
 */
public class Transaction extends Descriptive implements Serializable {
    /**
     * The account id
     */
    private long accountId;

    /**
     * The category id
     */
    private long categoryId;

    /**
     * The amount
     */
    private double amount;

    /**
     * The status
     * @see TransactionStatus
     */
    private byte status;

    /**
     * The transaction type
     */
    private byte transactionType;

    /**
     * The transaction source
     * @see TransactionSource
     */
    private byte transactionSource = TransactionSource.MANUAL.getValue();

    /**
     * The contact id
     */
    private long contactId = 0;

    /**
     * The transaction date
     */
    private LocalDateTime transactionDate;

    /**
     * The transfer details for transfer type
     * @see Transfer
     */
    private Transfer transfer = null;

    /**
     * Returns true if transaction type if transfer.
     *
     * @return true if transaction type if transfer
     */
    public boolean isTransferType() {
        return transactionType == TransactionType.TRANSFER.getValue();
    }

    /**
     * Gets transfer.
     *
     * @return the transfer
     */
    public Transfer getTransfer() {
        return transfer;
    }

    /**
     * Sets transfer.
     *
     * @param transfer the transfer
     */
    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

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
     * Getter for property 'categoryId'.
     *
     * @return Value for property 'categoryId'.
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Setter for property 'categoryId'.
     *
     * @param categoryId Value to set for property 'categoryId'.
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }


    /**
     * Getter for property 'amount'.
     *
     * @return Value for property 'amount'.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter for property 'amount'.
     *
     * @param amount Value to set for property 'amount'.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter for property 'status'.
     *
     * @return Value for property 'status'.
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Setter for property 'status'.
     *
     * @param status Value to set for property 'status'.
     */
    public void setStatus(byte status) {
        this.status = status;
    }

    /**
     * Getter for property 'transactionType'.
     *
     * @return Value for property 'transactionType'.
     */
    public byte getTransactionType() {
        return transactionType;
    }

    /**
     * Setter for property 'transactionType'.
     *
     * @param transactionType Value to set for property 'transactionType'.
     */
    public void setTransactionType(byte transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Getter for property 'transactionDate'.
     *
     * @return Value for property 'transactionDate'.
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Setter for property 'transactionDate'.
     *
     * @param transactionDate Value to set for property 'transactionDate'.
     */
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets transaction source.
     *
     * @return the transaction source
     */
    public byte getTransactionSource() {
        return transactionSource;
    }

    /**
     * Sets transaction source.
     *
     * @param transactionSource the transaction source
     */
    public void setTransactionSource(byte transactionSource) {
        this.transactionSource = transactionSource;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public long getContactId() {
        return contactId;
    }

    /**
     * Sets contact id.
     *
     * @param contactId the contact id
     */
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
