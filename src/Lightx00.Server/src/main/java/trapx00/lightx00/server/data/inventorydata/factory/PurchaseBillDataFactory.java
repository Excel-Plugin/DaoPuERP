package trapx00.lightx00.server.data.inventorydata.factory;

import trapx00.lightx00.server.data.inventorydata.PurchaseBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseBillDataService;

import java.rmi.RemoteException;

@Export
public class PurchaseBillDataFactory {
    private static PurchaseBillDataService service;

    public static PurchaseBillDataService getService() {
        if (service == null) {
            try {
                return service = new PurchaseBillDataController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return service;
    }
}
