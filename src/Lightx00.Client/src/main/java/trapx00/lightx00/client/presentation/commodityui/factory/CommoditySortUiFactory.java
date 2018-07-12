package trapx00.lightx00.client.presentation.commodityui.factory;

import trapx00.lightx00.client.presentation.commodityui.commoditySort.CommoditySortUiController;
import trapx00.lightx00.client.presentation.commodityui.mock.CommoditySortUiControllerMock;

public class CommoditySortUiFactory {
    private static CommoditySortUiController commodityUiController = new CommoditySortUiControllerMock();
    static {
        // 以后要改，controller应该是用fxmlloader拿
    }
}
