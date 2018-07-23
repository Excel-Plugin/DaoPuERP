package suwu.daopuerp.bl.productionbill.factory;

import suwu.daopuerp.bl.productionbill.ProductionBillBlServiceImpl;
import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;

public class ProductionBillBlServiceFactory {
    private static ProductionBillBlService productionBillBlService = new ProductionBillBlServiceImpl();

    public static ProductionBillBlService getProductionBillBlService() {
        return productionBillBlService;
    }
}
