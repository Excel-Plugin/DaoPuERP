package trapx00.lightx00.client.presentation.saleui.factory;

import trapx00.lightx00.client.presentation.saleui.SaleCommodityFillUiController;

public class SaleCommodityFillUiFactory {
    private static SaleCommodityFillUiController saleCommodityFillUiController = new SaleCommodityFillUiController();

    public static SaleCommodityFillUiController getSaleCommodityFillUiController() {
        return saleCommodityFillUiController;
    }
}
