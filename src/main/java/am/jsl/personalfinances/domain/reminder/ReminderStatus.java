package am.jsl.personalfinances.domain.reminder;

/**
 * An enum containing possible reminder statuses.
 *
 * @author hamlet
 */
public enum ReminderStatus {
    ACTIVE((byte) 1),
    DONE((byte) 2),
    DISABLED((byte) 3);

    private byte value;

    private ReminderStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
