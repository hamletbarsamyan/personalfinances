package am.jsl.personalfinances.domain.user;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Verification token is sent to user's email when creating users or resetting passwords.
 * It will expire within 24 hours following its creation.
 *
 * @author hamlet
 */
public class VerificationToken {

    /**
     * The expiration time of verification token (24 hours)
     */
    private static final int EXPIRATION = 60 * 24;

    /**
     * The internal identifier
     */
    private long id;

    /**
     *  The unique, randomly generated token
     */
    private String token;

    /**
     * The token type (NEW_ACCOUNT, PASSWORD_RESET)
     */
    private byte tokenType;

    /**
     * The user id associated with this verification token
     */
    private long userId;

    /**
     * The expiration date of this verification token
     */
    private Date expiryDate;

    /**
     * Indicates whether this verification token has expired
     */
    private boolean expired = false;

    /**
     * Default constructor.
     */
    public VerificationToken() {
        super();
    }

    /**
     * Calculates expiration date from current date.
     *
     * @param expiryTimeInMinutes The expiration time in minutes
     * @return The expiration date
     */
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    /**
     * Sets the token and calculates expire date for this verification token.
     *
     * @param token The token to set
     */
    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.expired = false;
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
     * Getter for property 'token'.
     *
     * @return Value for property 'token'.
     */
    public String getToken() {
        return token;
    }

    /**
     * Setter for property 'token'.
     *
     * @param token Value to set for property 'token'.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Getter for property 'tokenType'.
     *
     * @return Value for property 'tokenType'.
     */
    public byte getTokenType() {
        return tokenType;
    }

    /**
     * Setter for property 'tokenType'.
     *
     * @param tokenType Value to set for property 'tokenType'.
     */
    public void setTokenType(byte tokenType) {
        this.tokenType = tokenType;
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
     * Getter for property 'expiryDate'.
     *
     * @return Value for property 'expiryDate'.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Setter for property 'expiryDate'.
     *
     * @param expiryDate Value to set for property 'expiryDate'.
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Getter for property 'expired'.
     *
     * @return Value for property 'expired'.
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Setter for property 'expired'.
     *
     * @param expired Value to set for property 'expired'.
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, tokenType, userId, expiryDate, expired);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final VerificationToken other = (VerificationToken) obj;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.token, other.token)
                && Objects.equals(this.tokenType, other.tokenType)
                && Objects.equals(this.userId, other.userId)
                && Objects.equals(this.expiryDate, other.expiryDate)
                && Objects.equals(this.expired, other.expired);
    }
}
