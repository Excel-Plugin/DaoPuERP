package trapx00.lightx00.client.presentation.commodityui.factory;

import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelectingDialog;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommoditySelection;
import trapx00.lightx00.client.presentation.commodityui.commodity.CommodityUiController;
import trapx00.lightx00.client.presentation.commodityui.mock.CommodityUiControllerMock;

public class CommodityUiFactory {
    private static CommodityUiController commodityUiController = new CommodityUiControllerMock();

    public static CommoditySelection getCommoditySelectionUi(){
        return new CommoditySelectingDialog();
    }
}
