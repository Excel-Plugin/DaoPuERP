package trapx00.lightx00.client.blservice.commodityblservice;

import trapx00.lightx00.client.bl.commoditybl.factory.CommodityServiceFactory;

public class CommodityBlServiceFactory {

    /**
     * Gets a CommodityBlService instance.
     * @return CommodityBlService instance
     */
    public static CommodityBlService getInstance(){
        return CommodityServiceFactory.getController();
    }
}
