package trapx00.lightx00.server.data.financedata.factory;

import trapx00.lightx00.server.data.financedata.CashBillDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.financedataservice.CashBillDataService;

import java.rmi.RemoteException;

@Export
public class CashBillDataFactory {
    private static CashBillDataService service;

    static {
        try {
            service = new CashBillDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static CashBillDataService getService() {
        return service;
    }
}
