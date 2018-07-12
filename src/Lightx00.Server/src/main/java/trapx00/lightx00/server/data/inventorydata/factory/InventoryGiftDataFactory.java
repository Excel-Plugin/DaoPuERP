package trapx00.lightx00.server.data.inventorydata.factory;

import trapx00.lightx00.server.data.inventorydata.InventoryGiftDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryGiftDataService;

import java.rmi.RemoteException;

@Export
public class InventoryGiftDataFactory {

    private static InventoryGiftDataService controller;

    static{
        try {
            controller = new InventoryGiftDataController();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }


    public static InventoryGiftDataService getService(){
        return controller;
    }

}
