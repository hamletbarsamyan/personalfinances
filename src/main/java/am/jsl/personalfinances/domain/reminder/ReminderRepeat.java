package am.jsl.personalfinances.domain.reminder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * An enum containing possible reminder's repeat modes.
 *
 * @author hamlet
 */
public enum ReminderRepeat {
    NONE((byte) 0),
    DAILY((byte) 1),
    MONTHLY((byte) 2),
    YEARLY((byte) 3);

    private byte value;

    private static final Map<Byte, ReminderRepeat> lookup;

    static {
        lookup = new HashMap<>();

        Arrays.stream(values()).forEach(repeat -> lookup.put(repeat.getValue(), repeat));
    }

    ReminderRepeat(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ReminderRepeat get(byte value) {
        return lookup.get(value);
    }
}
