package trapx00.lightx00.server.data.logindata.factory;

import trapx00.lightx00.server.data.logindata.LoginDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.logindataservice.LoginDataService;

import java.rmi.RemoteException;

@Export
public class LoginDataFactory {
    private static LoginDataService service;

    static {
        try {
            service = new LoginDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static LoginDataService getService() {
        return service;
    }
}
