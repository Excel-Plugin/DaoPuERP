package trapx00.lightx00.client.bl.inventorybl.factory;

import trapx00.lightx00.client.bl.inventorybl.InventoryCheckBlController;

public class InventoryCheckServiceFactory {
    private static InventoryCheckBlController controller=new InventoryCheckBlController();

    public static InventoryCheckBlController getInventoryCheckBlController(){
        return controller;
    }

}
