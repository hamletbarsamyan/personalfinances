package am.jsl.personalfinances.dto.account;

import am.jsl.personalfinances.dto.DescriptiveDTO;

import java.io.Serializable;

/**
 * Used for transferring and displaying account details in account list pages.
 * @author hamlet
 */
public class AccountListDTO extends DescriptiveDTO implements Serializable {
    /**
     * The current balance of this account.
     */
    private double balance;

    /**
     * The type of this account.
     */
    private byte accountType;

    /**
     *  The ISO 4217 currency code of this account.
     */
    private String currency;

    /**
     * The native symbol of currency
     */
    private String symbol;

    /**
     * The status of account. True if is active.
     */
    private boolean active;

    /**
     * The icon of this account.
     */
    private String icon;

    /**
     * The color of this account.
     */
    private String color;

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
     * Getter for property 'accountType'.
     *
     * @return Value for property 'accountType'.
     */
    public byte getAccountType() {
        return accountType;
    }

    /**
     * Setter for property 'accountType'.
     *
     * @param accountType Value to set for property 'accountType'.
     */
    public void setAccountType(byte accountType) {
        this.accountType = accountType;
    }

    /**
     * Getter for property 'currency'.
     *
     * @return Value for property 'currency'.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Setter for property 'currency'.
     *
     * @param currency Value to set for property 'currency'.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter for property 'symbol'.
     *
     * @return Value for property 'symbol'.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Setter for property 'symbol'.
     *
     * @param symbol Value to set for property 'symbol'.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter for property 'active'.
     *
     * @return Value for property 'active'.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Setter for property 'active'.
     *
     * @param active Value to set for property 'active'.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter for property 'icon'.
     *
     * @return Value for property 'icon'.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Setter for property 'icon'.
     *
     * @param icon Value to set for property 'icon'.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Getter for property 'color'.
     *
     * @return Value for property 'color'.
     */
    public String getColor() {
        return color;
    }

    /**
     * Setter for property 'color'.
     *
     * @param color Value to set for property 'color'.
     */
    public void setColor(String color) {
        this.color = color;
    }
}
