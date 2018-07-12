package trapx00.lightx00.server.data.inventorydata.factory;

import trapx00.lightx00.server.data.inventorydata.PurchaseRefundBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.inventorydataservice.PurchaseRefundBillDataService;

import java.rmi.RemoteException;

@Export
public class PurchaseRefundBillDataFactory {
    private static PurchaseRefundBillDataService service;

    public static PurchaseRefundBillDataService getService() {
        if (service == null) {
            try {
                return service = new PurchaseRefundBillDataController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return service;
    }
}
