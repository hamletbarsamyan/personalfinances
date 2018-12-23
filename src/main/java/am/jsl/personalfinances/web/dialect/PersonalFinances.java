package am.jsl.personalfinances.web.dialect;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

/**
 * The PersonalFinances is custom Thymeleaf expression object for date time formatting purposes.
 *
 * @author hamlet
 */
public final class PersonalFinances {
    /**
     * Formatter Utils.
     */
    private PersonalFinancesFormattingUtils formattingUtils;

    /**
     * Creates a new PersonalFinances with given locale and time zone.
     * @param locale the Locale
     */
    public PersonalFinances(final Locale locale) {
        super();
        this.formattingUtils = new PersonalFinancesFormattingUtils(locale);
    }

    /**
     * Called by Thymeleaf engine for formatting the given date.
     * @param date the Date
     * @return string representing formatted date
     */
    public String prettyDate(final Date date) {
        return formattingUtils.prettyDate(date);
    }

    /**
     * Called by Thymeleaf engine for formatting the given timestamp.
     * @param timestamp the timestamp to format
     * @return string representing formatted timestamp
     */
    public String prettyTime(final Timestamp timestamp) {
        return formattingUtils.prettyTime(timestamp);
    }
}
