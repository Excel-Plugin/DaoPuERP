package trapx00.lightx00.client.datafactory.notificationdataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.notificationdataservice.NotificationDataService;
import trapx00.lightx00.shared.dataservicestub.notificationdataservice.NotificationDataServiceStub;

public class NotificationDataServiceFactory extends DataServiceFactory {
    private static NotificationDataService service = new NotificationDataServiceStub();

    public static void initRmi() {
        service = lookupService(NotificationDataService.class);
    }

    public static NotificationDataService getService() {
        initRmi();
        return service;
    }
}
