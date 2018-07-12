package trapx00.lightx00.client.datafactory.financedataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.financedataservice.ReceivalBillDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.ReceivalBillDataServiceStub;

public class ReceivalBillDataServiceFactory extends DataServiceFactory {
    private static ReceivalBillDataService service = new ReceivalBillDataServiceStub();

    public static void initRmi() {
        service = lookupService(ReceivalBillDataService.class);
    }

    public static ReceivalBillDataService getService() {
        initRmi();
        return service;
    }
}
