package suwu.daopuerp.dataservice.factory;

import suwu.daopuerp.data.productionbill.ProductionDataServiceImpl;
import suwu.daopuerp.dataservice.productionbill.ProductionBillDataService;

public class ProductionBillDataServiceFactory {
    private static ProductionBillDataService productionBillDataService = new ProductionDataServiceImpl();

    public static ProductionBillDataService getProductionBillDataService() {
        return productionBillDataService;
    }
}
