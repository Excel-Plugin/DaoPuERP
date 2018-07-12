package trapx00.lightx00.shared.util;

import static trapx00.lightx00.shared.util.DateHelper.currentDateString;

public class BillHelper {
    /**
     * Get date string used in bill (yyyyMMdd).
     * @return date string in yyyyMMdd
     */
    public static String currentDateStringForBill() {
        return currentDateString("yyyyMMdd");
    }

    /**
     * Formats id into a 5-digit string.
     * @param id id
     * @return 5-digit string
     */

    public static String formatId(int id) {
        return String.format("%05d",id);
    }

    public static String comFormaiId(int id){
        return String.format("%04d",id);
    }

    public static String toFixed(double num, int precision) {
        return String.format("%.2f", num);
    }

    public static String toFixed(double num) {
        return toFixed(num, 2);
    }

    public static String formatComid(int id){return String.format("%04d",id);}

    public static final String refreshIdRequest = "REFRESH_ID";
}
