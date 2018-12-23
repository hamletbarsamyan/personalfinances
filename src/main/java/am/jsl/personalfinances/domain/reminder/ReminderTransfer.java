package am.jsl.personalfinances.domain.reminder;

import java.io.Serializable;

/**
 * The transfer details domain object.
 *
 * @author hamlet
 */
public class ReminderTransfer implements Serializable {
    /**
     * The internal identifier
     */
    private long id;

    /**
     * The reminder id
     */
    private long reminderId;

    /**
     * The target account id
     */
    private long targetAccountId = 0;

    /**
     * The converted amount
     */
    private double convertedAmount = 0;

    /**
     * The exchange rate of currency which is used when calculated the convertedAmount
     */
    private double rate = 0;

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
     * Gets reminder id.
     *
     * @return the reminder id
     */
    public long getReminderId() {
        return reminderId;
    }

    /**
     * Sets reminder id.
     *
     * @param reminderId the reminder id
     */
    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }

    /**
     * Gets target account id.
     *
     * @return the target account id
     */
    public long getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * Sets target account id.
     *
     * @param targetAccountId the target account id
     */
    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    /**
     * Gets converted amount.
     *
     * @return the converted amount
     */
    public double getConvertedAmount() {
        return convertedAmount;
    }

    /**
     * Sets converted amount.
     *
     * @param convertedAmount the converted amount
     */
    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    /**
     * Gets rate.
     *
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Sets rate.
     *
     * @param rate the rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }
}
