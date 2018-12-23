package am.jsl.personalfinances.dto.reminder;

import am.jsl.personalfinances.domain.reminder.Reminder;
import am.jsl.personalfinances.domain.reminder.ReminderRepeat;
import am.jsl.personalfinances.domain.reminder.ReminderStatus;
import am.jsl.personalfinances.domain.reminder.ReminderTransfer;
import am.jsl.personalfinances.domain.transaction.TransactionType;
import am.jsl.personalfinances.util.DateUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The ReminderDTO is used for creating and updating reminder from web.
 *
 * @author hamlet
 */
public class ReminderDTO implements Serializable {

    /**
     * The internal identifier
     */
    private long id;

    /**
     * The name of reminder
     */
    private String name;

    /**
     * The description
     */
    private String description;

    /**
     * The account id of reminder
     */
    private long accountId;

    /**
     * The category id of reminder
     */
    private long categoryId;

    /**
     * The amount of this reminder which will be used for creating transactions
     */
    private double amount;

    /**
     * The status of reminder
     */
    private byte status;

    /**
     * The transaction type of reminder(expense, income, transfer)
     */
    private byte transactionType;

    /**
     * The due date of reminder
     */
    private String dueDate;

    /**
     * The contact id of reminder
     */
    private long contactId;

    /**
     * The exchange rate of currency which is used when calculated the convertedAmount
     */
    private double exchangeRate;

    /**
     * Target account id for transfer type
     */
    private long targetAccountId;

    /**
     * The converted amount
     */
    private double convertedAmount;

    /**
     * If auto charge is true then a transaction will be created automatically
     */
    private boolean autoCharge = false;

    /**
     * If repeat is true then this reminder will be processed repeatedly on each reminder job execution
     */
    private byte repeat;

    /**
     * The user id
     */
    private long userId;

    /**
     * Default constructor
     */
    public ReminderDTO() {
        super();
    }

    /**
     * Constructs new ReminderDTO with the given fields.
     *
     * @param accountId       the account id
     * @param transactionType the transaction type
     * @param dueDate         the due date
     * @param userId          the user id
     */
    public ReminderDTO(long accountId, byte transactionType, LocalDateTime dueDate, long userId) {
        super();
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.dueDate = DateUtils.format(dueDate);
        this.userId = userId;
        this.status = ReminderStatus.ACTIVE.getValue();
        this.repeat = ReminderRepeat.NONE.getValue();
    }

    /**
     * Returns true if the transaction type is transfer.
     *
     * @return true if the transaction type is transfer.
     */
    public boolean isTransfer() {
        return transactionType == TransactionType.TRANSFER.getValue();
    }

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
     * Getter for property 'categoryId'.
     *
     * @return Value for property 'categoryId'.
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Setter for property 'categoryId'.
     *
     * @param categoryId Value to set for property 'categoryId'.
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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
     * Getter for property 'status'.
     *
     * @return Value for property 'status'.
     */
    public byte getStatus() {
        return status;
    }

    /**
     * Setter for property 'status'.
     *
     * @param status Value to set for property 'status'.
     */
    public void setStatus(byte status) {
        this.status = status;
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
     * Gets due local date time.
     *
     * @return the due local date time
     */
    public LocalDateTime getDueLocalDateTime() {
        return DateUtils.parse(dueDate);
    }

    /**
     * Getter for property 'targetAccountId'.
     *
     * @return Value for property 'targetAccountId'.
     */
    public long getTargetAccountId() {
        return targetAccountId;
    }

    /**
     * Setter for property 'targetAccountId'.
     *
     * @param targetAccountId Value to set for property 'targetAccountId'.
     */
    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    /**
     * Getter for property 'convertedAmount'.
     *
     * @return Value for property 'convertedAmount'.
     */
    public double getConvertedAmount() {
        return convertedAmount;
    }

    /**
     * Setter for property 'convertedAmount'.
     *
     * @param convertedAmount Value to set for property 'convertedAmount'.
     */
    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
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
     * Getter for property 'exchangeRate'.
     *
     * @return Value for property 'exchangeRate'.
     */
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Setter for property 'exchangeRate'.
     *
     * @param exchangeRate Value to set for property 'exchangeRate'.
     */
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * Getter for property 'autoCharge'.
     *
     * @return Value for property 'autoCharge'.
     */
    public boolean isAutoCharge() {
        return autoCharge;
    }

    /**
     * Setter for property 'autoCharge'.
     *
     * @param autoCharge Value to set for property 'autoCharge'.
     */
    public void setAutoCharge(boolean autoCharge) {
        this.autoCharge = autoCharge;
    }

    /**
     * Getter for property 'repeat'.
     *
     * @return Value for property 'repeat'.
     */
    public byte getRepeat() {
        return repeat;
    }

    /**
     * Setter for property 'repeat'.
     *
     * @param repeat Value to set for property 'repeat'.
     */
    public void setRepeat(byte repeat) {
        this.repeat = repeat;
    }


    /**
     * Getter for property 'name'.
     *
     * @return Value for property 'name'.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     *
     * @param name Value to set for property 'name'.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets due date.
     *
     * @return the due date
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets due date.
     *
     * @param dueDate the due date
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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
     * Converts this DTO to reminder domain object.
     *
     * @return The reminder domain object
     */
    public Reminder toReminder() {
        Reminder reminder = new Reminder();
        reminder.setId(getId());
        reminder.setName(getName());
        reminder.setAccountId(getAccountId());
        reminder.setCategoryId(getCategoryId());
        reminder.setAmount(getAmount());
        reminder.setStatus(getStatus());
        reminder.setTransactionType(getTransactionType());
        reminder.setDueDate(getDueLocalDateTime());
        reminder.setContactId(getContactId());
        reminder.setDescription(getDescription());
        reminder.setRepeat(getRepeat());
        reminder.setAutoCharge(isAutoCharge());

        // create transfer details
        if (reminder.isTransfer()) {
            ReminderTransfer reminderTransfer = new ReminderTransfer();
            reminderTransfer.setReminderId(getId());
            reminderTransfer.setTargetAccountId(getTargetAccountId());
            reminderTransfer.setRate(getExchangeRate());
            reminderTransfer.setConvertedAmount(getConvertedAmount());
            reminder.setReminderTransfer(reminderTransfer);
        }

        return reminder;
    }

    /**
     * Converts reminder domain object to ReminderDTO.
     *
     * @param reminder The reminder to convert
     * @return The ReminderDTO
     */
    public static ReminderDTO from(Reminder reminder) {
        ReminderDTO dto = new ReminderDTO();
        dto.setId(reminder.getId());
        dto.setName(reminder.getName());
        dto.setAccountId(reminder.getAccountId());
        dto.setCategoryId(reminder.getCategoryId());
        dto.setAmount(reminder.getAmount());
        dto.setStatus(reminder.getStatus());
        dto.setTransactionType(reminder.getTransactionType());
        dto.setDueDate(DateUtils.format(reminder.getDueDate()));
        dto.setUserId(reminder.getUserId());

        dto.setRepeat(reminder.getRepeat());
        dto.setAutoCharge(reminder.isAutoCharge());
        dto.setContactId(reminder.getContactId());
        dto.setDescription(reminder.getDescription());

        // transfer details
        ReminderTransfer reminderTransfer = reminder.getReminderTransfer();

        if (reminderTransfer != null) {
            dto.setTargetAccountId(reminderTransfer.getTargetAccountId());
            dto.setExchangeRate(reminderTransfer.getRate());
            dto.setConvertedAmount(reminderTransfer.getConvertedAmount());
        }

        return dto;
    }
}
