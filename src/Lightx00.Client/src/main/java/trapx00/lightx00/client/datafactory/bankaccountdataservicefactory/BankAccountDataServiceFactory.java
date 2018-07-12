package trapx00.lightx00.client.datafactory.bankaccountdataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.bankaccountdataservice.BankAccountDataService;
import trapx00.lightx00.shared.dataservicestub.bankaccountdataservice.BankAccountDataServiceStub;

public class BankAccountDataServiceFactory extends DataServiceFactory {
    private static BankAccountDataService dataService = new BankAccountDataServiceStub();

    private static void initRmi() {
        dataService = lookupService(BankAccountDataService.class);
    }

    public static BankAccountDataService getDataService() {
        initRmi();
        return dataService;
    }
}
