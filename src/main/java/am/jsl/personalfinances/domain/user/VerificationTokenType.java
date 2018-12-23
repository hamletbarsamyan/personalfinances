package am.jsl.personalfinances.domain.user;

/**
 * An enum containing possible verification token types.
 *
 * @author hamlet
 */
public enum VerificationTokenType {
    NEW_ACCOUNT((byte) 1),
    PASSWORD_RESET ((byte) 2);

    private byte value;

    VerificationTokenType(byte value) {
        this.value = value;
    }

    /**
     * Getter for property 'value'.
     *
     * @return Value for property 'value'.
     */
    public byte getValue() {
        return value;
    }
}
