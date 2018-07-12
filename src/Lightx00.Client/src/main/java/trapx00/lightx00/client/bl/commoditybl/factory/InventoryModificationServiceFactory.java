package trapx00.lightx00.client.bl.commoditybl.factory;

import trapx00.lightx00.client.bl.commoditybl.CommodityBlController;
import trapx00.lightx00.client.bl.commoditybl.InventoryModificationService;

public class InventoryModificationServiceFactory {
    private static InventoryModificationService service=new CommodityBlController();

    public static InventoryModificationService getService(){
        return service;
    }
}
