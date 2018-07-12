package trapx00.lightx00.server.data.financedata.factory;

import trapx00.lightx00.server.data.financedata.ReceivalBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.financedataservice.ReceivalBillDataService;

import java.rmi.RemoteException;

@Export
public class ReceivalBillDataFactory {
    private static ReceivalBillDataService service;

    static {
        try {
            service = new ReceivalBillDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ReceivalBillDataService getService() {
        return service;
    }
}
