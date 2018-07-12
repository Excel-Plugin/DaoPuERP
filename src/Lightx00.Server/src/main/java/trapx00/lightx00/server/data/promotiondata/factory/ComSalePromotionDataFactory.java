package trapx00.lightx00.server.data.promotiondata.factory;

import trapx00.lightx00.server.data.promotiondata.ComSalePromotionDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;

import java.rmi.RemoteException;

@Export
public class ComSalePromotionDataFactory {
    private static ComSalePromotionDataService service;

    static {
        try {
            service = new ComSalePromotionDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ComSalePromotionDataService getService() {
        return service;
    }
}
