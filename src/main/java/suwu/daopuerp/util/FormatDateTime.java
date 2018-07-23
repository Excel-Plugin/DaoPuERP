package suwu.daopuerp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class FormatDateTime {

    public static String toLongDateString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        return myFmt.format(dt);
    }

    public static String toShortDateString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        return myFmt.format(dt);
    }

    public static String toShortDateString(Date dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        return myFmt.format(dt);
    }

    public static Date fromShortDateString(String dt) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yy年MM月dd日HH时mm分");
        try {
            return myFmt.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date fromLocalDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String toLongTimeString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("HH mm ss SSSS");
        return myFmt.format(dt);
    }

    public static String toShortTimeString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yy/MM/dd");
        return myFmt.format(dt);
    }

    /**
     * Get date string used in bill (yyyyMMdd).
     *
     * @return date string in yyyyMMdd
     */
    public static String currentDateStringForBill() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyyMMdd");
        return myFmt.format(dt);
    }

    public static String currentDateString() {
        Date dt = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月");
        return myFmt.format(dt);
    }
}
