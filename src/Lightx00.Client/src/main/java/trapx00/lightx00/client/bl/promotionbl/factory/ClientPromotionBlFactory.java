package trapx00.lightx00.client.bl.promotionbl.factory;

import trapx00.lightx00.client.bl.draftbl.DraftDeleteService;
import trapx00.lightx00.client.bl.promotionbl.ClientPromotionBlController;

public class ClientPromotionBlFactory {
    //工厂是用来创建对象的
    private static ClientPromotionBlController controller = new ClientPromotionBlController();
    private static DraftDeleteService draftDeleteService = controller;


    public static ClientPromotionBlController getController() {
        return controller;
    }

    public static DraftDeleteService getDraftDeleteService() {
        return draftDeleteService;
    }

}
