package trapx00.lightx00.client.datafactory.commoditydataservicefactory;

import trapx00.lightx00.shared.dataservice.commoditydataservice.CommoditySortDataService;
import trapx00.lightx00.shared.dataservicestub.commoditydataservice.CommoditySortDataServiceStub;

import static trapx00.lightx00.client.datafactory.DataServiceFactory.lookupService;

public class CommoditySortDataServiceFactory {
    private static CommoditySortDataService service = new CommoditySortDataServiceStub();

    public static void initRmi() {
        service = lookupService(CommoditySortDataService.class);
    }

    public static  CommoditySortDataService getService() {
        initRmi();
        return service;
    }
}
