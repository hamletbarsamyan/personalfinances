package am.jsl.personalfinances.domain.reminder;

import am.jsl.personalfinances.domain.Descriptive;
import am.jsl.personalfinances.domain.transaction.TransactionType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The reminder domain object.
 * The reminders will be processed by reminder job based on reminder's due date
 * and transaction(s) will be created based this reminder configuration:
 * account, category, amount, transaction type, contact.
 *
 * @author hamlet
 */
public class Reminder extends Descriptive implements Serializable {

    /**
     * The status of reminder
     * @see ReminderStatus
     */
    private byte status;

    /**
     * The due date of reminder
     */
    private LocalDateTime dueDate;

    /**
     * The account id of reminder
     */
    private long accountId;

    /**
     * The category id of reminder
     */
    private long categoryId;

    /**
     * The amount of this reminder which will be used for creating transactions
     */
    private double amount;

    /**
     * The transaction type of reminder(expense, income, transfer)
     * @see TransactionType
     */
    private byte transactionType;

    /**
     * The contact id of reminder
     */
    private long contactId;

    /**
     * If auto charge is true then a transaction will be created automatically
     */
    private boolean autoCharge;

    /**
     * If repeat is true then this reminder will be processed repeatedly on each reminder job execution
     * @see ReminderRepeat
     */
    private byte repeat;

    /**
     * Transfer transaction details
     * @see ReminderTransfer
     */
    private ReminderTransfer reminderTransfer = null;

    /**
     * Returns true if the transaction type is transfer.
     *
     * @return true if the transaction type is transfer.
     */
    public boolean isTransfer() {
        return transactionType == TransactionType.TRANSFER.getValue();
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
     * Getter for property 'contactId'.
     *
     * @return Value for property 'contactId'.
     */
    public long getContactId() {
        return contactId;
    }

    /**
     * Setter for property 'contactId'.
     *
     * @param contactId Value to set for property 'contactId'.
     */
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for property 'autoCharge'.
     *
     * @return Value for property 'autoCharge'.
     */
    public boolean isAutoCharge() {
        return autoCharge;
    }

    /**
     * Setter for property 'autoCharge'.
     *
     * @param autoCharge Value to set for property 'autoCharge'.
     */
    public void setAutoCharge(boolean autoCharge) {
        this.autoCharge = autoCharge;
    }

    /**
     * Getter for property 'repeat'.
     *
     * @return Value for property 'repeat'.
     */
    public byte getRepeat() {
        return repeat;
    }

    /**
     * Setter for property 'repeat'.
     *
     * @param repeat Value to set for property 'repeat'.
     */
    public void setRepeat(byte repeat) {
        this.repeat = repeat;
    }

    /**
     * Getter for property 'dueDate'.
     *
     * @return Value for property 'dueDate'.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Setter for property 'dueDate'.
     *
     * @param dueDate Value to set for property 'dueDate'.
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets reminder transfer.
     *
     * @return the reminder transfer
     */
    public ReminderTransfer getReminderTransfer() {
        return reminderTransfer;
    }

    /**
     * Sets reminder transfer.
     *
     * @param reminderTransfer the reminder transfer
     */
    public void setReminderTransfer(ReminderTransfer reminderTransfer) {
        this.reminderTransfer = reminderTransfer;
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
        final Reminder other = (Reminder) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
