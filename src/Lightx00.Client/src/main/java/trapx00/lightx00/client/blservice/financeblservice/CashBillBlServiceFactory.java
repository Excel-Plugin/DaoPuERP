package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.CashBillBlFactory;

public class CashBillBlServiceFactory {
    public static CashBillBlService getInstance() {
        return CashBillBlFactory.getController();
    }
}
