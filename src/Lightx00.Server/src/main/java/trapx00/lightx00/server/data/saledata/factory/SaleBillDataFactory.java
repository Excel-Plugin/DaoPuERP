package trapx00.lightx00.server.data.saledata.factory;

import trapx00.lightx00.server.data.saledata.SaleBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.saledataservice.SaleBillDataService;

import java.rmi.RemoteException;

@Export
public class SaleBillDataFactory {
    private static SaleBillDataService service;

    public static SaleBillDataService getService() {
        if (service == null) {
            try {
                return service = new SaleBillDataController();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return service;
    }
}
