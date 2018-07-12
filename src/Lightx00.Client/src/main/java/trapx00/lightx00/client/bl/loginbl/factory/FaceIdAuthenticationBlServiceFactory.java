package trapx00.lightx00.client.bl.loginbl.factory;

import trapx00.lightx00.client.bl.loginbl.FaceIdAuthenticationBlController;
import trapx00.lightx00.client.blservice.loginblservice.FaceIdAuthenticationBlService;

public class FaceIdAuthenticationBlServiceFactory {
    private static FaceIdAuthenticationBlService service = new FaceIdAuthenticationBlController();

    public static FaceIdAuthenticationBlService getService() {
        return service;
    }
}
