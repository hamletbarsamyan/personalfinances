package am.jsl.personalfinances.domain.reminder;

import java.io.Serializable;

/**
 * The mapping of reminder to transaction.
 *
 * @author hamlet
 */
public class ReminderTransaction implements Serializable {

    /**
     * The reminder id
     */
    private long reminderId;

    /**
     * The transaction id
     */
    private long transactionId;

    /**
     * Getter for property 'reminderId'.
     *
     * @return Value for property 'reminderId'.
     */
    public long getReminderId() {
        return reminderId;
    }

    /**
     * Setter for property 'reminderId'.
     *
     * @param reminderId Value to set for property 'reminderId'.
     */
    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }

    /**
     * Getter for property 'transactionId'.
     *
     * @return Value for property 'transactionId'.
     */
    public long getTransactionId() {
        return transactionId;
    }

    /**
     * Setter for property 'transactionId'.
     *
     * @param transactionId Value to set for property 'transactionId'.
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
