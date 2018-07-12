package trapx00.lightx00.client.presentation.saleui.factory;

import trapx00.lightx00.client.presentation.saleui.SaleBillUiController;
import trapx00.lightx00.client.presentation.saleui.mock.SaleBillUiControllerMock;

public class SaleBillUiFactory {
    private static SaleBillUiController saleBillUiController = new SaleBillUiControllerMock();

    public static SaleBillUiController getSaleBillUiController(){
        return saleBillUiController;
    }
}
