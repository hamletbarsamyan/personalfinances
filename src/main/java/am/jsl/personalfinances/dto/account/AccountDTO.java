package am.jsl.personalfinances.dto.account;

import am.jsl.personalfinances.domain.account.Account;
import am.jsl.personalfinances.dto.DescriptiveDTO;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serializable;

/**
 * Used for transferring account details between web pages and controllers.
 * @author hamlet
 */
public class AccountDTO extends DescriptiveDTO implements Serializable {

    /**
     * The current balance of this account.
     */
    @NumberFormat(pattern = "#,##0.00")
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
    private boolean active = true;

    /**
     * The icon of this account.
     */
    private String icon;

    /**
     * The color of this account.
     */
    private String color;

    /**
     * The sort order
     */
    private int sortOrder;

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

    /**
     * Getter for property 'sortOrder'.
     *
     * @return Value for property 'sortOrder'.
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Setter for property 'sortOrder'.
     *
     * @param sortOrder Value to set for property 'sortOrder'.
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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

    public Account toAccount() {
        Account account = new Account();
        account.setId(getId());
        account.setName(getName());
        account.setDescription(getDescription());
        account.setBalance(getBalance());
        account.setAccountType(getAccountType());
        account.setCurrency(getCurrency());
        account.setSymbol(getSymbol());
        account.setActive(isActive());
        account.setSortOrder(getSortOrder());
        account.setIcon(getIcon());
        account.setColor(getColor());
        return account;
    }

    public static AccountDTO from(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setDescription(account.getDescription());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setCurrency(account.getCurrency());
        dto.setSymbol(account.getSymbol());
        dto.setActive(account.isActive());
        dto.setSortOrder(account.getSortOrder());
        dto.setIcon(account.getIcon());
        dto.setColor(account.getColor());
        return dto;
    }
}
