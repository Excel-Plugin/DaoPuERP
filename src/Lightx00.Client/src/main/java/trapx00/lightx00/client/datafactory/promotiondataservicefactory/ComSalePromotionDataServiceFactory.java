package trapx00.lightx00.client.datafactory.promotiondataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ComSalePromotionDataService;

public class ComSalePromotionDataServiceFactory extends DataServiceFactory {

    private static  ComSalePromotionDataService service;

    private static void initRmi() {
        service = lookupService(ComSalePromotionDataService.class);
    }

    public static  ComSalePromotionDataService getService() {
        initRmi();
        return service;
    }
}
