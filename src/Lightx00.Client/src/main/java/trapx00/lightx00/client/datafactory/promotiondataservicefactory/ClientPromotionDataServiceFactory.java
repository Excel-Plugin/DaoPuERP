package trapx00.lightx00.client.datafactory.promotiondataservicefactory;

import trapx00.lightx00.client.datafactory.DataServiceFactory;
import trapx00.lightx00.shared.dataservice.promotiondataservice.ClientPromotionDataService;

public class ClientPromotionDataServiceFactory extends DataServiceFactory {

    private static ClientPromotionDataService service;

    private static void initRmi() {
        service = lookupService(ClientPromotionDataService.class);
    }

    public static ClientPromotionDataService getService() {
        initRmi();
        return service;
    }
}
