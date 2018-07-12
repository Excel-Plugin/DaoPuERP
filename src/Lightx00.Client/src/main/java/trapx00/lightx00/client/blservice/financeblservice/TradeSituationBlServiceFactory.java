package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.TradeSituationBlFactory;

public class TradeSituationBlServiceFactory {

    public static TradeSituationBlService getInstance() {
        return TradeSituationBlFactory.getController();
    }

}
