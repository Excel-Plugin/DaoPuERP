package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.bl.inventorybl.factory.InventoryGiftServiceFactory;

public class InventoryGiftBlServiceFactory {


    /**
     * Gets a InventoryGiftBlService instance.
     * @return InventoryGiftBlService instance
     */
    public static InventoryGiftBlService getInstance(){
        return InventoryGiftServiceFactory.getInventoryGiftBlController();
    }
}
