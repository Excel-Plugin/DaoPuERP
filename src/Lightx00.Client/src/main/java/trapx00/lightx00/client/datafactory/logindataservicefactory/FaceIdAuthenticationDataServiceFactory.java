package trapx00.lightx00.client.datafactory.logindataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.logindataservice.FaceIdAuthenticationDataService;
import trapx00.lightx00.shared.dataservicestub.logindataservice.FaceIdAuthenticationDataServiceStub;

public class FaceIdAuthenticationDataServiceFactory extends DataServiceFactory {
    private static FaceIdAuthenticationDataService service = new FaceIdAuthenticationDataServiceStub();

    public static void initRmi() {
        service = lookupService(FaceIdAuthenticationDataService.class);
    }

    public static FaceIdAuthenticationDataService getService() {
        initRmi();
        return service;
    }
}
