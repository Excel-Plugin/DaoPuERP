package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.TradeHistoryBlFactory;

public class TradeHistoryBlServiceFactory {
    public  static TradeHistoryBlService getInstance() {
        return TradeHistoryBlFactory.getController();
    }
}
