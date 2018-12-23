package am.jsl.personalfinances.dto.reminder;

import java.io.Serializable;

/**
 * The ReminderListDTO is used for displaying reminders in reminder list page.
 *
 * @author hamlet
 */
public class ReminderListDTO extends BaseReminderDTO implements Serializable {

    /**
     * The user id
     */
    private long userId;

    /**
     * If repeat is true then this reminder will be processed repeatedly on each reminder job execution
     */
    private byte repeat;

    /**
     * If auto charge is true then a transaction will be created automatically
     */
    private boolean autoCharge;

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
}
