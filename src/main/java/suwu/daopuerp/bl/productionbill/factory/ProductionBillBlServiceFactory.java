package suwu.daopuerp.bl.productionbill.factory;

import suwu.daopuerp.bl.productionbill.ProductionBillServiceImpl;
import suwu.daopuerp.blservice.productionbill.ProductionBillService;

public class ProductionBillBlServiceFactory {
    private static ProductionBillService productionBillService = new ProductionBillServiceImpl();

    public static ProductionBillService getProductionBillService() {
        return productionBillService;
    }
}
