package trapx00.lightx00.server.data.commoditydata.factory;

import trapx00.lightx00.server.data.commoditydata.CommodityDataController;
import trapx00.lightx00.server.data.util.export.Export;
import trapx00.lightx00.shared.dataservice.commoditydataservice.CommodityDataService;

import java.rmi.RemoteException;

@Export
public class CommodityDataFactory  {

    private static CommodityDataService controller;

    static{
        try {
            controller = new CommodityDataController();
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }
    public static CommodityDataService getService(){
        return controller;
    }
}
