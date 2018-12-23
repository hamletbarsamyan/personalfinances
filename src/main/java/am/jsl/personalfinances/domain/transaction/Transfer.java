package am.jsl.personalfinances.domain.transaction;

import java.io.Serializable;

/**
 * The transfer domain object.
 *
 * @author hamlet
 */
public class Transfer  implements Serializable {
    /**
     * The internal identifier
     */
    private long id;

    /**
     * The transaction id
     */
    private long transactionId;

    /**
     * The target account id
     */
    private long targetAccountId = 0;

    /**
     * The converted amount that will be transferred to target account
     */
    private double convertedAmount = 0;

    /**
     * The exchange rate
     */
    private double rate = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
