package trapx00.lightx00.client.datafactory.financedataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.financedataservice.InitialEstablishmentDataService;
import trapx00.lightx00.shared.dataservicestub.financedataservice.InitialEstablishmentDataServiceStub;

public class InitialEstablishmentDataServiceFactory extends DataServiceFactory {
    private static InitialEstablishmentDataService service = new InitialEstablishmentDataServiceStub();

    public static void initRmi() {
        service = lookupService(InitialEstablishmentDataService.class);
    }

    public static InitialEstablishmentDataService getService() {
        initRmi();
        return service;
    }
}
