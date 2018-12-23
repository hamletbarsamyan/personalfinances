package am.jsl.personalfinances.domain.transaction;

import java.io.Serializable;

/**
 * An enum containing possible transaction sources.
 *
 * @author hamlet
 */
public enum TransactionSource implements Serializable {

    /**
     * Manual source indicates user created transactions (income, expense, transfer)
     */
    MANUAL((byte) 1),

    /**
     * Lend source indicates transactions created by reminder job
     */
    REMINDER((byte) 2);

    private byte value;

    TransactionSource(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
