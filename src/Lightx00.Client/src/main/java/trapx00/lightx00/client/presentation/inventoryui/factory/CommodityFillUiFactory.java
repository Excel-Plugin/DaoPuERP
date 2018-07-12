package trapx00.lightx00.client.presentation.inventoryui.factory;

import trapx00.lightx00.client.presentation.inventoryui.CommodityFillUiController;

public class CommodityFillUiFactory {
    private static CommodityFillUiController commodityFillUiController = new CommodityFillUiController();

    public static CommodityFillUiController getCommodityFillUiController() {
        return commodityFillUiController;
    }
}
