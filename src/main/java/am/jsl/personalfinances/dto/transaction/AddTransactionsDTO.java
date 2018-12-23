package am.jsl.personalfinances.dto.transaction;

import am.jsl.personalfinances.domain.transaction.Transaction;
import am.jsl.personalfinances.util.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The AddTransactionsDTO is used batch tranasctions from add batch transaction page.
 *
 * @author hamlet
 */
public class AddTransactionsDTO implements Serializable {

    /**
     * The description
     */
    private String description;

    /**
     * The account id
     */
    private long accountId;

    /**
     * The transaction type
     */
    private byte transactionType;

    /**
     * The transaction date
     */
    private String transactionDate;

    /**
     * The contact id
     */
    private long contactId;

    /**
     * The user id
     */
    private long userId;

    /**
     * The lsit of transactions to be created
     */
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Default constructor
     */
    public AddTransactionsDTO() {
        super();
    }

    /**
     * Constructs new AddTransactionsDTO with the given fields.
     *
     * @param accountId       the account id
     * @param transactionType the transaction type
     * @param transactionDate the transaction date
     * @param userId          the user id
     */
    public AddTransactionsDTO(long accountId, byte transactionType, LocalDateTime transactionDate, long userId) {
        super();
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.transactionDate = DateUtils.format(transactionDate);
        this.userId = userId;
    }

    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for property 'description'.
     *
     * @param description Value to set for property 'description'.
     */
    public void setDescription(String description) {
        this.description = description;
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
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Setter for property 'transactionDate'.
     *
     * @param transactionDate Value to set for property 'transactionDate'.
     */
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
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

    /**
     * Add transaction.
     *
     * @param transaction the transaction
     */
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Getter for property 'transactions'.
     *
     * @return Value for property 'transactions'.
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Setter for property 'transactions'.
     *
     * @param transactions Value to set for property 'transactions'.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
