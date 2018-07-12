package trapx00.lightx00.client.presentation.promotionui.factory;

import trapx00.lightx00.client.presentation.promotionui.detail.ClientPromotionUiController;
import trapx00.lightx00.client.presentation.promotionui.mock.ClientPromotionUiControllerMock;

public class ClientPromotionUiFactory {
    private static ClientPromotionUiController controller = new ClientPromotionUiControllerMock();

    public static ClientPromotionUiController getController() {
        return controller;
    }
}
