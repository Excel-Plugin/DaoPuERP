package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.SaleDetailBlFactory;

public class SaleDetailBlServiceFactory {

    public static SaleDetailBlService getInstance() {
        return SaleDetailBlFactory.getController();
    }
}
