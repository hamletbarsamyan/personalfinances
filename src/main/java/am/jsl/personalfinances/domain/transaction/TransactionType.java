package am.jsl.personalfinances.domain.transaction;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An enum containing possible transaction types.
 * @author hamlet
 */
public enum TransactionType {
    EXPENSE((byte) 1),
    INCOME((byte) 2),
    TRANSFER((byte) 3);

    private byte value;
    private static final Map<Byte, TransactionType> types;

    static {
        types = new HashMap<>();

        Arrays.stream(values()).forEach(type -> types.put(type.getValue(), type));
    }

    public static TransactionType get(byte value) {
        return types.get(value);
    }

    TransactionType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
