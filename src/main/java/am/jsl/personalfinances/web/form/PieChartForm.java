package am.jsl.personalfinances.web.form;

import java.io.Serializable;

/**
 * The PieChartForm is used for searching transaction from pi chart page.
 * @author hamlet
 */
public class PieChartForm implements Serializable {
    /**
     * The transaction type
     */
    private short type;
    /**
     * The transaction account
     */
    private long account;

    /**
     * The transaction date range
     */
    private String daterange;

    /**
     * Getter for property 'type'.
     *
     * @return Value for property 'type'.
     */
    public short getType() {
        return type;
    }

    /**
     * Setter for property 'type'.
     *
     * @param type Value to set for property 'type'.
     */
    public void setType(short type) {
        this.type = type;
    }

    /**
     * Getter for property 'account'.
     *
     * @return Value for property 'account'.
     */
    public long getAccount() {
        return account;
    }

    /**
     * Setter for property 'account'.
     *
     * @param account Value to set for property 'account'.
     */
    public void setAccount(long account) {
        this.account = account;
    }

    /**
     * Getter for property 'daterange'.
     *
     * @return Value for property 'daterange'.
     */
    public String getDaterange() {
        return daterange;
    }

    /**
     * Setter for property 'daterange'.
     *
     * @param daterange Value to set for property 'daterange'.
     */
    public void setDaterange(String daterange) {
        this.daterange = daterange;
    }
}
