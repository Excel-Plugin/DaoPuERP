package trapx00.lightx00.client.blservice.saleblservice;

import trapx00.lightx00.client.bl.salebl.factory.SaleRefundBillBlFactory;

public class SaleRefundBillBlServiceFactory {
    private static SaleRefundBillBlService saleRefundBillBlService = SaleRefundBillBlFactory.getSaleRefundBillBlController();

    public static SaleRefundBillBlService getInstance() {
        return saleRefundBillBlService;
    }
}
