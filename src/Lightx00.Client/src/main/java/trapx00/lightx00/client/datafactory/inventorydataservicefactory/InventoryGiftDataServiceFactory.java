package trapx00.lightx00.client.datafactory.inventorydataservicefactory;

import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;
import trapx00.lightx00.shared.dataservicestub.inventorydataservice.InventoryGiftDataServiceStub;

import static trapx00.lightx00.client.datafactory.DataServiceFactory.lookupService;

public class InventoryGiftDataServiceFactory {
    private static InventoryGiftDataService service = new InventoryGiftDataServiceStub();

    public static void initRmi() {
        service = lookupService(InventoryGiftDataService.class);
    }

    public static  InventoryGiftDataService getService() {
        initRmi();
        return service;
    }


}
