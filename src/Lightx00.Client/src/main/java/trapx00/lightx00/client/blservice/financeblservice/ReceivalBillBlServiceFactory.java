package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.ReceivalBillBlFactory;

public class ReceivalBillBlServiceFactory {


    public static ReceivalBillBlService getInstance() {
        return ReceivalBillBlFactory.getController();
    }
}
