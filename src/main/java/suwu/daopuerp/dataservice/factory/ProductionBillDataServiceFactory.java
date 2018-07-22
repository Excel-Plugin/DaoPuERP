package suwu.daopuerp.dataservice.factory;

import suwu.daopuerp.data.productionbill.ProductionBillDataServiceImpl;
import suwu.daopuerp.dataservice.productionbill.ProductionBillDataService;

public class ProductionBillDataServiceFactory {
    private static ProductionBillDataService productionBillDataService = new ProductionBillDataServiceImpl();

    public static ProductionBillDataService getProductionBillDataService() {
        return productionBillDataService;
    }
}
