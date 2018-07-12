package trapx00.lightx00.client.presentation.promotionui.factory;

import trapx00.lightx00.client.presentation.promotionui.detail.TotalPricePromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.mock.TotalPricePromotionUiControllerMock;

public class TotalPricePromotionUiFactory {
    private static TotalPricePromotionUiController controller = new TotalPricePromotionUiControllerMock();

    public static TotalPricePromotionUiController getController() {
        return controller;
    }
}
