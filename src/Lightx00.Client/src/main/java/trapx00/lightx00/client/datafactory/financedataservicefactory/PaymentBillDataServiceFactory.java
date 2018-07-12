package trapx00.lightx00.client.datafactory.financedataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.financedataservice.PaymentBillDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.PaymentBillDataServiceStub;

public class PaymentBillDataServiceFactory extends DataServiceFactory {
    private static PaymentBillDataService service = new PaymentBillDataServiceStub();

    public static void initRmi() {
        service = lookupService(PaymentBillDataService.class);
    }

    public static PaymentBillDataService getService() {
        initRmi();
        return service;
    }
}
