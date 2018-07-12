package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseRefundBillBlFactory;

public class PurchaseRefundBillBlServiceFactory {
    private static PurchaseRefundBillBlService purchaseRefundBillBlService= PurchaseRefundBillBlFactory.getPurchaseRefundBillBlController();

    public static PurchaseRefundBillBlService getInstance(){
        return purchaseRefundBillBlService;
    }
}
