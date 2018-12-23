package am.jsl.personalfinances.dto.account;

import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

/**
 * Used for transferring adjust account balance from web.
 * @author hamlet
 */
public class AdjustBalanceDTO implements Serializable {
    /**
     * The account id
     */
    private long id;

    /**
     * The balance to be set
     */
    @NumberFormat(pattern = "#,##0.00")
    private double balance;

    /**
     * The account's user id
     */
    private long userId;

    /**
     * Getter for property 'id'.
     *
     * @return Value for property 'id'.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for property 'id'.
     *
     * @param id Value to set for property 'id'.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for property 'balance'.
     *
     * @return Value for property 'balance'.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Setter for property 'balance'.
     *
     * @param balance Value to set for property 'balance'.
     */
    public void setBalance(double balance) {
        this.balance = balance;
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
}
