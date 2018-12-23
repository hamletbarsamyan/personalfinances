package am.jsl.personalfinances.domain.transaction;

/**
 * An enum containing possible transaction statuses.
 *
 * @author hamlet
 */
public enum TransactionStatus {
    PENDING((byte) 1),
    DONE((byte) 2);

    private byte value;

    TransactionStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
