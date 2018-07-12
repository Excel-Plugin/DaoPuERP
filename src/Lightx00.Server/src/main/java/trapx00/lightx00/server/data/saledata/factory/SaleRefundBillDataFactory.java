package trapx00.lightx00.server.data.saledata.factory;

import trapx00.lightx00.server.data.saledata.SaleRefundBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleRefundBillDataService;

import java.rmi.RemoteException;

@Export
public class SaleRefundBillDataFactory {
    private static SaleRefundBillDataService service;

    public static SaleRefundBillDataService getService() {
        if (service == null) {
            try {
                service = new SaleRefundBillDataController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return service;
    }
}
