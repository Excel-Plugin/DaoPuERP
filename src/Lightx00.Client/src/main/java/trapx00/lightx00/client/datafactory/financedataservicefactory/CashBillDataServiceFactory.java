package trapx00.lightx00.client.datafactory.financedataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.financedataservice.CashBillDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.CashBillDataServiceStub;

public class CashBillDataServiceFactory  extends DataServiceFactory {
    private static CashBillDataService service = new CashBillDataServiceStub();

    public static void initRmi() {
        service = lookupService(CashBillDataService.class);
    }

    public static CashBillDataService getService() {
        initRmi();
        return service;
    }
}
