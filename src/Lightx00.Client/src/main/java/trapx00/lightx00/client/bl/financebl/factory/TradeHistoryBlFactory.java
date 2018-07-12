package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.financebl.FinanceBillInfo;
import trapx00.lightx00.client.bl.financebl.TradeHistoryBlController;

public class TradeHistoryBlFactory {
    private static TradeHistoryBlController controller = new TradeHistoryBlController();
    private static FinanceBillInfo financeBillInfo = controller;

    public static TradeHistoryBlController getController() {
        return controller;
    }

    public static FinanceBillInfo getFinanceBillInfo() {
        return financeBillInfo;
    }
}
