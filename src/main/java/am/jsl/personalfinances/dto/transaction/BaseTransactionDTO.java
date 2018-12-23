package am.jsl.personalfinances.dto.transaction;

import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.dto.DescriptiveDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * The BaseTransactionDTO contains transaction details for transferring between application layers.
 * @author hamlet
 */
public class BaseTransactionDTO extends DescriptiveDTO implements Serializable {
    /**
     * The native currency symbol of account
     */
    protected String symbol;

    /**
     * The category name of this transaction
     */
    protected String category;

    /**
     * The category icon of this transaction
     */
    protected String categoryIcon;

    /**
     * The category color of this transaction
     */
    protected String categoryColor;

    /**
     * The name of parent category
     */
    protected String parentCategory;

    /**
     * The icon of parent category
     */
    protected String parentCategoryIcon;

    /**
     * The color of parent category
     */
    protected String parentCategoryColor;

    /**
     * The amount of this transaction
     */
    protected double amount;

    /**
     * The type of this transaction
     */
    protected byte transactionType;

    /**
     * The date of this transaction
     */
    protected Date transactionDate;

    /**
     * Returns true if transaction type if expense.
     *
     * @return true if transaction type if expense
     */
    public boolean isExpense() {
        return transactionType == TransactionType.EXPENSE.getValue();
    }

    /**
     * Returns true if transaction type if income.
     *
     * @return true if transaction type if income
     */
    public boolean isIncome() {
        return transactionType == TransactionType.INCOME.getValue();
    }

    /**
     * Returns true if transaction type if transfer.
     *
     * @return true if transaction type if transfer
     */
    public boolean isTransfer() {
        return transactionType == TransactionType.TRANSFER.getValue();
    }

    /**
     * Returns css class for transaction type.
     *
     * @return true if transaction type if transfer
     */
    public String getTransactionTypeClass() {
        return "trType" + transactionType;
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
     * Getter for property 'category'.
     *
     * @return Value for property 'category'.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Setter for property 'category'.
     *
     * @param category Value to set for property 'category'.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Getter for property 'categoryIcon'.
     *
     * @return Value for property 'categoryIcon'.
     */
    public String getCategoryIcon() {
        return categoryIcon;
    }

    /**
     * Setter for property 'categoryIcon'.
     *
     * @param categoryIcon Value to set for property 'categoryIcon'.
     */
    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    /**
     * Getter for property 'categoryColor'.
     *
     * @return Value for property 'categoryColor'.
     */
    public String getCategoryColor() {
        return categoryColor;
    }

    /**
     * Setter for property 'categoryColor'.
     *
     * @param categoryColor Value to set for property 'categoryColor'.
     */
    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }

    /**
     * Getter for property 'parentCategory'.
     *
     * @return Value for property 'parentCategory'.
     */
    public String getParentCategory() {
        return parentCategory;
    }

    /**
     * Setter for property 'parentCategory'.
     *
     * @param parentCategory Value to set for property 'parentCategory'.
     */
    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * Getter for property 'parentCategoryIcon'.
     *
     * @return Value for property 'parentCategoryIcon'.
     */
    public String getParentCategoryIcon() {
        return parentCategoryIcon;
    }

    /**
     * Setter for property 'parentCategoryIcon'.
     *
     * @param parentCategoryIcon Value to set for property 'parentCategoryIcon'.
     */
    public void setParentCategoryIcon(String parentCategoryIcon) {
        this.parentCategoryIcon = parentCategoryIcon;
    }

    /**
     * Getter for property 'parentCategoryColor'.
     *
     * @return Value for property 'parentCategoryColor'.
     */
    public String getParentCategoryColor() {
        return parentCategoryColor;
    }

    /**
     * Setter for property 'parentCategoryColor'.
     *
     * @param parentCategoryColor Value to set for property 'parentCategoryColor'.
     */
    public void setParentCategoryColor(String parentCategoryColor) {
        this.parentCategoryColor = parentCategoryColor;
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
     * Getter for property 'transactionDate'.
     *
     * @return Value for property 'transactionDate'.
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Setter for property 'transactionDate'.
     *
     * @param transactionDate Value to set for property 'transactionDate'.
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
