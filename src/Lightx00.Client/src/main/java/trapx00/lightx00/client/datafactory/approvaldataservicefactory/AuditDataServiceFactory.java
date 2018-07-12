package trapx00.lightx00.client.datafactory.approvaldataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.approvaldataservice.AuditDataService;
import trapx00.lightx00.shared.dataservicestub.approvaldataservice.AuditDataServiceStub;

public class AuditDataServiceFactory extends DataServiceFactory {

    private static AuditDataService service = new AuditDataServiceStub();

    public static void initRmi() {
        service = lookupService(AuditDataService.class);
    }

    public static AuditDataService getService() {
        initRmi();
        return service;
    }

}
