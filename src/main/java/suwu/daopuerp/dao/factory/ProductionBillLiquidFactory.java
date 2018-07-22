package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.productionbill.ProductionBillDao;
import suwu.daopuerp.daoimpl.productionbill.ProductionBillOilDaoImpl;

public class ProductionBillLiquidFactory {
    private static ProductionBillDao productionBillDao = new ProductionBillOilDaoImpl();

    public static ProductionBillDao getProductionBillDao() {
        return productionBillDao;
    }
}
