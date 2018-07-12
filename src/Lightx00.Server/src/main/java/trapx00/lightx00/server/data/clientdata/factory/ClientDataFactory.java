package trapx00.lightx00.server.data.clientdata.factory;

import trapx00.lightx00.server.data.clientdata.ClientDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.clientdataservice.ClientDataService;

import java.rmi.RemoteException;

@Export
public class ClientDataFactory {
    private static ClientDataService service;

    static {
        try {
            service = new ClientDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static ClientDataService getService() {
        return service;
    }
}
