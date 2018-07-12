package trapx00.lightx00.server.data.faceid.factory;

import trapx00.lightx00.server.data.faceid.FaceIdAuthenticationController;
import trapx00.lightx00.server.data.faceid.FaceIdAuthenticationService;

public class FaceIdAuthenticationServiceFactory {
    public static FaceIdAuthenticationService service = new FaceIdAuthenticationController();

    public static FaceIdAuthenticationService getService() {
        return service;
    }
}
