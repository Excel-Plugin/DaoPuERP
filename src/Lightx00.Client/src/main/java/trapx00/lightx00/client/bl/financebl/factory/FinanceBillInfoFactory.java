package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.financebl.FinanceBillInfo;

public class FinanceBillInfoFactory {
    private static FinanceBillInfo financeBillInfo = TradeHistoryBlFactory.getController();

    /**
     * Gets a FinanceBillInfo instance
     * @return FinanceBillInfo instance
     */
    public static FinanceBillInfo getFinanceBillInfo() {
        return financeBillInfo;
    }
}
