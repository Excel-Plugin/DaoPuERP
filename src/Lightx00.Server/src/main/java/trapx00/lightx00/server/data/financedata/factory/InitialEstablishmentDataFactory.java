package trapx00.lightx00.server.data.financedata.factory;

import trapx00.lightx00.server.data.financedata.InitialEstablishmentDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.financedataservice.InitialEstablishmentDataService;

import java.rmi.RemoteException;

@Export
public class InitialEstablishmentDataFactory {
    private static InitialEstablishmentDataService service;

    static {
        try {
            service = new InitialEstablishmentDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static InitialEstablishmentDataService getService() {
        return service;
    }
}
