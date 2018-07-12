package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.bl.inventorybl.factory.PurchaseBillBlFactory;

public class PurchaseBillBlServiceFactory {
    private static PurchaseBillBlService purchaseBillBlService = PurchaseBillBlFactory.getPurchaseBillBlController();

    public static PurchaseBillBlService getInstance() {
        return purchaseBillBlService;
    }
}
