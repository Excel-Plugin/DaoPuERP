package trapx00.lightx00.client.blservice.commodityblservice;

import trapx00.lightx00.client.bl.commoditybl.factory.CommoditySortServiceFactory;

public class CommoditySortBlServiceFactory {

    /**
     * Gets a CommoditySortBlServiceinstance.
     * @return CommoditySortBlService instance
     */
    public static CommoditySortBlService getInstance(){
        return CommoditySortServiceFactory.getController();
    }
}
