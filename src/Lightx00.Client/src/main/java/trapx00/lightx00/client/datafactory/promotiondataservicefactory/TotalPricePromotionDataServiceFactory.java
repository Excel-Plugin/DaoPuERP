package trapx00.lightx00.client.datafactory.promotiondataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.TotalPricePromotionDataService;

public class TotalPricePromotionDataServiceFactory extends DataServiceFactory {
    private static TotalPricePromotionDataService service;

    private static void initRmi() {
        service = lookupService(TotalPricePromotionDataService.class);
    }

    public static TotalPricePromotionDataService getService() {
        initRmi();
        return service;
    }
}
