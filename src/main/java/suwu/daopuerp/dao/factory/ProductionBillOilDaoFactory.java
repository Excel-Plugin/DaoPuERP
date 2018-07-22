package suwu.daopuerp.dao.factory;

import suwu.daopuerp.dao.productionbill.ProductionBillOilDao;
import suwu.daopuerp.daoimpl.productionbill.ProductionBillOilDaoImpl;

public class ProductionBillOilDaoFactory {
    private static ProductionBillOilDao productionBillOilDao = new ProductionBillOilDaoImpl();

    public static ProductionBillOilDao getProductionBillOilDao() {
        return productionBillOilDao;
    }
}
