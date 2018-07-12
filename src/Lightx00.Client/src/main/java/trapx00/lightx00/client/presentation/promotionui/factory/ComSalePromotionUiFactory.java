package trapx00.lightx00.client.presentation.promotionui.factory;

import trapx00.lightx00.client.presentation.promotionui.detail.ComSalePromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.mock.ComSalePromotionUiControllerMock;

public class ComSalePromotionUiFactory {
    private static ComSalePromotionUiController controller = new ComSalePromotionUiControllerMock();

    public static ComSalePromotionUiController getController() {
        return controller;
    }
}
