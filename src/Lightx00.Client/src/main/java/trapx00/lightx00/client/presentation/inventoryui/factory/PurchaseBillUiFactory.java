package trapx00.lightx00.client.presentation.inventoryui.factory;

import trapx00.lightx00.client.presentation.inventoryui.PurchaseBillUiController;
import trapx00.lightx00.client.presentation.inventoryui.mock.PurchaseBillUiControllerMock;

public class PurchaseBillUiFactory {
    private static PurchaseBillUiController purchaseBillUiController = new PurchaseBillUiControllerMock();

    public static PurchaseBillUiController getPurchaseBillUiController() {
        return purchaseBillUiController;
    }
}
