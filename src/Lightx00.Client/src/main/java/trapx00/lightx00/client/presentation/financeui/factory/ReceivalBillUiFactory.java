package trapx00.lightx00.client.presentation.financeui.factory;

import trapx00.lightx00.client.presentation.financeui.mock.ReceivalBillUiControllerMock;
import trapx00.lightx00.client.presentation.financeui.paymentandreceival.ReceivalBillUiController;
import trapx00.lightx00.client.presentation.helpui.DraftContinueWritableUiController;

public class ReceivalBillUiFactory {
    private static ReceivalBillUiController receivalBillUiController = new ReceivalBillUiControllerMock();

    public static DraftContinueWritableUiController getReceivalBillContinueWritable() {
        return receivalBillUiController;
    }
}
