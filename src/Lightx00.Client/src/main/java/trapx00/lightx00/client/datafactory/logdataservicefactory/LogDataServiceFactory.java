package trapx00.lightx00.client.datafactory.logdataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.logdataservice.LogDataService;
import trapx00.lightx00.shared.dataservicestub.logdataservice.LogDataServiceStub;

public class LogDataServiceFactory extends DataServiceFactory {
    private static LogDataService service = new LogDataServiceStub();

    public static void initRmi() {
        service = lookupService(LogDataService.class);
    }

    public static LogDataService getService() {
        initRmi();
        return service;
    }
}
