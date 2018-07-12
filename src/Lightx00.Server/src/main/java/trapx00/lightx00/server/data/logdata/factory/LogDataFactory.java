package trapx00.lightx00.server.data.logdata.factory;

import trapx00.lightx00.server.data.logdata.LogDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.logdataservice.LogDataService;

import java.rmi.RemoteException;

@Export
public class LogDataFactory {
    private static LogDataService service;

    static {
        try {
            service = new LogDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static LogDataService getService() {
        return service;
    }
}
