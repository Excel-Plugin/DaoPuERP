package trapx00.lightx00.shared.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {
    private static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern(DATE_PATTERN).withZone(ZoneId.systemDefault());

    /**
     * Get formalized date string according to the millisecond.
     * @param millisecond timestamp in millisecond
     * @return formalized date string
     */
    public static String fromTimestamp(long millisecond) {
        return dateFormatter.format(Instant.ofEpochMilli(millisecond));
    }

    /**
     * Formalize data according to the date.
     * @param date date
     * @return formalized date string
     */
    public static String fromDate(Date date) {
        return dateFormatter.format(date.toInstant());
    }

    /**
     * Get formalized current date string with default DATE_PATTERN.
     * @return formalized current date string
     */
    public static String currentDateString() {
        return fromTimestamp(System.currentTimeMillis());
    }

    /**
     * Get formalized current date string with custom dataPattern.
     * @return formalized current date string in custom date pattern
     */
    public static String currentDateString(String dataPattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dataPattern).withZone(ZoneId.systemDefault());
        return formatter.format(Instant.ofEpochMilli(System.currentTimeMillis()));
    }

    public static String currentDateStringForLog() {
        return currentDateString("yyyy-MM-dd HH:mm:ss.SSS");
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        return instant.atZone(zoneId).toLocalDate();
    }
}
