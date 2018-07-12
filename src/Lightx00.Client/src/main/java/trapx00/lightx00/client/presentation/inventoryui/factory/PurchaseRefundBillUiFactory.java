package trapx00.lightx00.client.presentation.inventoryui.factory;

import trapx00.lightx00.client.presentation.inventoryui.PurchaseRefundBillUiController;
import trapx00.lightx00.client.presentation.inventoryui.mock.PurchaseRefundBillUiControllerMock;

public class PurchaseRefundBillUiFactory {
    private static PurchaseRefundBillUiController purchaseRefundBillUiController = new PurchaseRefundBillUiControllerMock();

    public static PurchaseRefundBillUiController getPurchaseRefundBillUiController() {
        return purchaseRefundBillUiController;
    }
}
