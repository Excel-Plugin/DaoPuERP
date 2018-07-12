package trapx00.lightx00.server.data.promotiondata.factory;

import trapx00.lightx00.server.data.promotiondata.ClientPromotionDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;

import java.rmi.RemoteException;

@Export
public class ClientPromotionDataFactory {
    private static ClientPromotionDataService service;

    static {
        try {
            service = new ClientPromotionDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ClientPromotionDataService getService() {
        return service;
    }
}
