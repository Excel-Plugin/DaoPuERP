package trapx00.lightx00.server.data.promotiondata.factory;

import trapx00.lightx00.server.data.promotiondata.TotalPricePromotionDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;

import java.rmi.RemoteException;

@Export
public class TotalPricePromotionDataFactory {
    private static TotalPricePromotionDataService service;

    static {
        try {
            service = new TotalPricePromotionDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static TotalPricePromotionDataService getService() {
        return service;
    }
}
