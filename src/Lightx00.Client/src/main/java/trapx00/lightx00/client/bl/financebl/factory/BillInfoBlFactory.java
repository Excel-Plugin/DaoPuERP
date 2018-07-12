package trapx00.lightx00.client.bl.financebl.factory;

import trapx00.lightx00.client.bl.financebl.BillInfo;

public class BillInfoBlFactory {
    private static BillInfo billInfo = TradeHistoryBlFactory.getController();

    public static BillInfo getBillInfo() {
        return billInfo;
    }
}
