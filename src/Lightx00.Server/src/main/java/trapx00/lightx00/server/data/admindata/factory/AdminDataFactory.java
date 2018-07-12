package trapx00.lightx00.server.data.admindata.factory;

import trapx00.lightx00.server.data.admindata.LoginService;
import trapx00.lightx00.server.data.admindata.UserManagementDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.admindataservice.UserManagementDataService;

import java.rmi.RemoteException;

@Export
public class AdminDataFactory {
    private static UserManagementDataController controller;

    static {
        try {
            controller = new UserManagementDataController();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static LoginService getLoginService() {
        return controller;
    }

    public static UserManagementDataService getService() {
        return controller;
    }
}
