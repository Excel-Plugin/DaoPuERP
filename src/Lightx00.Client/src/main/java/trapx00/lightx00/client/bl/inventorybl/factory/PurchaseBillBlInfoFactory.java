package trapx00.lightx00.client.bl.inventorybl.factory;

import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfo;
import trapx00.lightx00.client.bl.inventorybl.PurchaseBillBlInfoController;

public class PurchaseBillBlInfoFactory {
    private static PurchaseBillBlInfo purchaseBillBlInfo = new PurchaseBillBlInfoController();

    public static PurchaseBillBlInfo getPurchaseBillBlInfo() {
        return purchaseBillBlInfo;
    }
}
