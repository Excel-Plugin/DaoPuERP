package trapx00.lightx00.client.blservice.adminblservice;

import trapx00.lightx00.client.bl.adminbl.factory.FaceIdRegistrationBlFactory;

public class FaceIdRegistrationBlServiceFactory {
    private static FaceIdRegistrationBlService service = FaceIdRegistrationBlFactory.getService();

    public static FaceIdRegistrationBlService getService() {
        return service;
    }
}
