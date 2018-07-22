package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.productionbill.ProductionBillLiquidDao;
import suwu.daopuerp.daoimpl.productionbill.ProductionBillLiquidDaoImpl;

public class ProductionBillLiquidFactory {
    private static ProductionBillLiquidDao productionBillLiquidDao = new ProductionBillLiquidDaoImpl();

    public static ProductionBillLiquidDao getProductionBillLiquidDao() {
        return productionBillLiquidDao;
    }
}
