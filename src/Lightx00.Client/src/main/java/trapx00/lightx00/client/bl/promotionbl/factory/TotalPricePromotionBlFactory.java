package trapx00.lightx00.client.bl.promotionbl.factory;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.promotionbl.TotalPricePromotionBlController;

public class TotalPricePromotionBlFactory {
    private static TotalPricePromotionBlController controller = new TotalPricePromotionBlController();
    private static DraftDeleteService draftDeleteService = controller;

    public static TotalPricePromotionBlController getController() {
        return controller;
    }

    public static DraftDeleteService getDraftDeleteService() {
        return draftDeleteService;
    }

}
