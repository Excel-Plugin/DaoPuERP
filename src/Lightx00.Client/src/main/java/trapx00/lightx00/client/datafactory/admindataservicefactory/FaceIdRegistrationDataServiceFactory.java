package trapx00.lightx00.client.datafactory.admindataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.admindataservice.FaceIdRegistrationDataService;

public class FaceIdRegistrationDataServiceFactory extends DataServiceFactory {
    private static FaceIdRegistrationDataService dataService = lookupService(FaceIdRegistrationDataService.class);

    private static void initRmi() {
        dataService = lookupService(FaceIdRegistrationDataService.class);
    }

    public static FaceIdRegistrationDataService getDataService() {
        initRmi();
        return dataService;
    }
}
