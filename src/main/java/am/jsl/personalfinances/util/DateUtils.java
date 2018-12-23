package am.jsl.personalfinances.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static am.jsl.personalfinances.util.Constants.DATE_FORMAT;
import static am.jsl.personalfinances.util.Constants.DATE_TIME_FORMAT;

/**
 * Contains methods for working with dates.
 * @author hamlet
 */
public class DateUtils {

	public static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	public static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final DateFormat DATE_TIME_FORMATTER = new SimpleDateFormat(DATE_TIME_FORMAT);

	public static final String toString(Date date, String format) {
		if (date == null) {
			return "";
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static final String toString(Date date) {
		if (date == null) {
			return "";
		}
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(date);
	}

	public static Date toDate(String dateString, String dateFormat)
			throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.parse(dateString);
	}

    public static long daysDiff(Date from, Date to) {
        return daysDiff(from.getTime(), to.getTime());
    }

    public static long daysDiff(long from, long to) {
        return Math.round( (to - from) / 86400000D ); // 1000 * 60 * 60 * 24
    }

	public static Timestamp convert(LocalDateTime localDateTime) {
		return localDateTime == null ? null :
				Timestamp.valueOf(localDateTime);
	}

	public static java.sql.Date convert(LocalDate localDate) {
		return localDate == null ? null : java.sql.Date.valueOf(localDate);
	}

	public static LocalDateTime convert(java.sql.Timestamp timestamp) {
		return timestamp == null ? null : timestamp.toLocalDateTime();
	}

	public static Timestamp convert(LocalTime localTime) {
		return localTime == null ? null : Timestamp.valueOf(
				localTime.atDate(LocalDate.ofEpochDay(0)));
	}

	public static String format(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return "";
		}

		return localDateTime.format(LOCAL_DATE_TIME_FORMATTER);
	}

	public static String format(LocalDate localDate) {
		if (localDate == null) {
			return "";
		}

		return localDate.format(LOCAL_DATE_FORMATTER);
	}

	public static final String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIME_FORMATTER.format(date);
	}

	public static Date parseDateTime(String dateTime) throws ParseException {
		return TextUtils.isEmpty(dateTime) ? null : DATE_TIME_FORMATTER.parse(dateTime);
	}

	public static LocalDateTime parse(String localDateTime) {
		return TextUtils.isEmpty(localDateTime) ? null : LocalDateTime.parse(localDateTime, LOCAL_DATE_TIME_FORMATTER);
	}

	public static LocalDate asLocalDate(java.util.Date date) {
		return asLocalDate(date, ZoneId.systemDefault());
	}


	public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Date) {
			return ((java.sql.Date) date).toLocalDate();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
		}
	}

	public static LocalDateTime toLocalDateTime(java.util.Date date) {
		return toLocalDateTime(date, ZoneId.systemDefault());
	}

	public static LocalDateTime toLocalDateTime(java.util.Date date, ZoneId zone) {
		if (date == null) {
			return null;
		}

		if (date instanceof java.sql.Timestamp) {
			return ((java.sql.Timestamp) date).toLocalDateTime();
		} else {
			return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
		}
	}
}
