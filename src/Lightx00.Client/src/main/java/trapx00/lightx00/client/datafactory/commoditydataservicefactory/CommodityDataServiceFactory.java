package trapx00.lightx00.client.datafactory.commoditydataservicefactory;

import trapx00.lightx00.shared.dataservice.commoditydataservice.CommodityDataService;
import trapx00.lightx00.shared.dataservicestub.commoditydataservice.CommodityDataServiceStub;

import static trapx00.lightx00.client.datafactory.DataServiceFactory.lookupService;

public class CommodityDataServiceFactory {
    private static CommodityDataService service = new CommodityDataServiceStub();

    public static void initRmi() {
        service = lookupService(CommodityDataService.class);
    }

    public static  CommodityDataService getService() {
        initRmi();
        return service;
    }
}
