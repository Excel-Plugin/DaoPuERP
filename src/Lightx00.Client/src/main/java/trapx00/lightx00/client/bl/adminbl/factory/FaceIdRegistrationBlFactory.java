package trapx00.lightx00.client.bl.adminbl.factory;

import trapx00.lightx00.client.bl.adminbl.FaceIdRegistrationBlController;
import trapx00.lightx00.client.blservice.adminblservice.FaceIdRegistrationBlService;

public class FaceIdRegistrationBlFactory {
    private static FaceIdRegistrationBlService service = new FaceIdRegistrationBlController();

    public static FaceIdRegistrationBlService getService() {
        return service;
    }
}
