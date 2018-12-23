package am.jsl.personalfinances.dto.reminder;

import am.jsl.personalfinances.domain.reminder.ReminderStatus;
import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.dto.DescriptiveDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * The BaseReminderDTO contains reminder data for transferring between application layers.
 * @author hamlet
 */
public class BaseReminderDTO extends DescriptiveDTO implements Serializable {
    /**
     * The status of reminder
     */
    protected short status;

    /**
     * The native currency symbol of this reminder account
     */
    protected String symbol;

    /**
     * The category name of reminder
     */
    protected String category;

    /**
     * The category icon
     */
    protected String categoryIcon;

    /**
     * The category color
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
     * The amount of this reminder
     */
    protected String parentCategoryColor;

    /**
     * The amount of this reminder which will be used for creating transactions
     */
    protected double amount;

    /**
     * The transaction type of reminder(expense, income, transfer)
     */
    protected byte transactionType;

    /**
     * The due date of reminder
     */
    private Date dueDate;

    /**
     * Returns true if transaction type is expense.
     * @return true if transaction type is expense
     */
    public boolean isExpense() {
        return transactionType == TransactionType.EXPENSE.getValue();
    }

    /**
     * Returns true if transaction type is income.
     * @return true if transaction type is income
     */
    public boolean isIncome() {
        return transactionType == TransactionType.INCOME.getValue();
    }

    /**
     * Returns true if transaction type is transfer.
     * @return true if transaction type is transfer
     */
    public boolean isTransfer() {
        return transactionType == TransactionType.TRANSFER.getValue();
    }

    /**
     * Returns true if reminder status is active.
     * @return true if reminder status is active
     */
    public boolean isActive() {
        return status == ReminderStatus.ACTIVE.getValue();
    }

    /**
     * Returns true if reminder status is disabled.
     * @return true if reminder status is disabled
     */
    public boolean isDisabled() {
        return status == ReminderStatus.DISABLED.getValue();
    }

    /**
     * Returns true if reminder status is done.
     * @return true if reminder status is done
     */
    public boolean isDone() {
        return status == ReminderStatus.DONE.getValue();
    }

    /**
     * Returns css class for transaction type.
     * @return the css class for transaction type
     */
    public String getTransactionTypeClass() {
        return "trType" + transactionType;
    }

    /**
     * Returns css class for reminder status.
     * @return the css class for reminder status
     */
    public String getStatusClass() {
        return "remStatus" + status;
    }

    /**
     * Getter for property 'status'.
     *
     * @return Value for property 'status'.
     */
    public short getStatus() {
        return status;
    }

    /**
     * Setter for property 'status'.
     *
     * @param status Value to set for property 'status'.
     */
    public void setStatus(short status) {
        this.status = status;
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
     * Getter for property 'dueDate'.
     *
     * @return Value for property 'dueDate'.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Setter for property 'dueDate'.
     *
     * @param dueDate Value to set for property 'dueDate'.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
