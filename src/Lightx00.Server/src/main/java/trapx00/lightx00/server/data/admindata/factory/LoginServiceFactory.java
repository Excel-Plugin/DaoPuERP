package trapx00.lightx00.server.data.admindata.factory;

import trapx00.lightx00.server.data.admindata.LoginService;

public class LoginServiceFactory {
    private static LoginService service = AdminDataFactory.getLoginService();

    public static LoginService getService() {
        return service;
    }
}
