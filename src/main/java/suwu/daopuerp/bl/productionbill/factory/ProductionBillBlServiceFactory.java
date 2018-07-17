package suwu.daopuerp.bl.productionbill.factory;

import suwu.daopuerp.bl.productionbill.mock.ProductionBlServiceMock;
import suwu.daopuerp.blservice.productionbill.ProductionBillService;

public class ProductionBillBlServiceFactory {
    private static ProductionBillService productionBillService = new ProductionBlServiceMock();

    public static ProductionBillService getProductionBillService() {
        return productionBillService;
    }
}
