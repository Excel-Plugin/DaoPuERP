package trapx00.lightx00.shared.util;

import static trapx00.lightx00.shared.util.DateHelper.currentDateString;

public class PromotionHelper {
    /**
     * Get date string used in promotion (yyyyMMdd).
     * @return date string in yyyyMMdd
     */
    public static String currentDateStringForPromotion() {
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
}
