package trapx00.lightx00.server.data.logindata.factory;

import trapx00.lightx00.server.data.logindata.FaceIdAuthenticationDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;

import java.rmi.RemoteException;

@Export
public class FaceIdAuthenticationDataFactory {
    private static FaceIdAuthenticationDataService dataService;

    static {
        try {
            dataService = new FaceIdAuthenticationDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static FaceIdAuthenticationDataService getService() {
        return dataService;
    }
}
