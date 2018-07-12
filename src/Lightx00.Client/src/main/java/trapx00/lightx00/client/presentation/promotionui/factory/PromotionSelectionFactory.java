package trapx00.lightx00.client.presentation.promotionui.factory;

import trapx00.lightx00.client.presentation.promotionui.PromotionSelection;
import trapx00.lightx00.client.presentation.promotionui.PromotionSelectionUiController;

public class PromotionSelectionFactory {
    private static PromotionSelection promotionSelection = new PromotionSelectionUiController();

    public static PromotionSelection getPromotionSelection() {
        return promotionSelection;
    }
}
