package trapx00.lightx00.server.data.inventorydata.factory;

import trapx00.lightx00.server.data.inventorydata.InventoryWarningDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.inventorydataservice.InventoryWarningDataService;

import java.rmi.RemoteException;

@Export
public class InventoryWarningDataFactory {

    private static InventoryWarningDataService controller;

    static{
        try {
            controller = new InventoryWarningDataController();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    public static InventoryWarningDataService getService(){
        return controller;
    }
}
