package trapx00.lightx00.server.data.commoditydata.factory;

import trapx00.lightx00.server.data.commoditydata.CommoditySortDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;

import java.rmi.RemoteException;

@Export
public class CommoditySortDataFactory {
    private static CommoditySortDataService controller;

    static {
        try {
            controller = new CommoditySortDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static CommoditySortDataService getService(){
        return controller;
    }
}
