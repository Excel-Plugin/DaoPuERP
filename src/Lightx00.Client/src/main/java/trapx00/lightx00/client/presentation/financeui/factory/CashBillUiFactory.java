package trapx00.lightx00.client.presentation.financeui.factory;

import trapx00.lightx00.client.presentation.financeui.cashbill.CashBillUiController;
import trapx00.lightx00.client.presentation.financeui.mock.CashBillUiControllerMock;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;

public class CashBillUiFactory {
    private static CashBillUiController cashBillUiController = new CashBillUiControllerMock();
    static {
        // 以后要改，controller应该是用fxmlloader拿
    }

    public static DraftContinueWritableUiController getCashBillContinueWritable() {
        return cashBillUiController;
    }
}
