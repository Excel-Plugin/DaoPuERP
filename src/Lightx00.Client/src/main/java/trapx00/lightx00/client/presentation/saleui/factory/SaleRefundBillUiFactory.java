package trapx00.lightx00.client.presentation.saleui.factory;

import trapx00.lightx00.client.presentation.saleui.SaleRefundBillUiController;
import trapx00.lightx00.client.presentation.saleui.mock.SaleRefundBillUiControllerMock;

public class SaleRefundBillUiFactory {
    private static SaleRefundBillUiController saleRefundBillUiController = new SaleRefundBillUiControllerMock();

    public static SaleRefundBillUiController getSaleRefundBillUiController() {
        return saleRefundBillUiController;
    }
}
