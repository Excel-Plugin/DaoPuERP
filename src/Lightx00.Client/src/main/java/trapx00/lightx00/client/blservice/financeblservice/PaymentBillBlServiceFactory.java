package trapx00.lightx00.client.blservice.financeblservice;

import trapx00.lightx00.client.bl.financebl.factory.PaymentBillBlFactory;

public class PaymentBillBlServiceFactory {

    public static PaymentBillBlService getInstance() {
        return PaymentBillBlFactory.getController();
    }

}
