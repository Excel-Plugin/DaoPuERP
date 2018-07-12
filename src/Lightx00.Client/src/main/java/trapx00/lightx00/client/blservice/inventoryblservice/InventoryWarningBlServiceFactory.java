package trapx00.lightx00.client.blservice.inventoryblservice;

import trapx00.lightx00.client.bl.inventorybl.factory.InventoryWarningServiceFactory;

public class InventoryWarningBlServiceFactory {

    /**
     * Gets a InventoryWarningBlServiceinstance.
     * @return InventoryWarningBlService instance
     */
    public static InventoryWarningBlService getInstance() {
        return InventoryWarningServiceFactory.getInventoryWarningBlController();
    }
}
