package am.jsl.personalfinances.dto.transaction;

import am.jsl.personalfinances.domain.transaction.TransactionType;

import java.io.Serializable;

/**
 * The TransactionListTotalDTO is used for displaying transaction totals grouped by transaction type and account.
 *
 * @author hamlet
 */
public class TransactionListTotalDTO implements Serializable {

    /**
     * The transaction type
     */
    private byte transactionType;

    /**
     * The total amount
     */
    private double total;

    /**
     * The native symbol of currency
     */
    private String symbol;

    /**
     * Returns css class for transaction type
     *
     * @return the css class
     */
    public String getTransactionTypeClass() {
        return "trType" + transactionType;
    }

    /**
     * Returns true if transaction type is expense.
     *
     * @return true if transaction type is expense.
     */
    public boolean isExpense() {
        return transactionType == TransactionType.EXPENSE.getValue();
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
     * Getter for property 'total'.
     *
     * @return Value for property 'total'.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Setter for property 'total'.
     *
     * @param total Value to set for property 'total'.
     */
    public void setTotal(double total) {
        this.total = total;
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
}
