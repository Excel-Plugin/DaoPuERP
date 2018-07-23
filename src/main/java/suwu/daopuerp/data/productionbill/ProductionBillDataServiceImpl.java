package suwu.daopuerp.data.productionbill;

import suwu.daopuerp.dao.factory.ProductionBillLiquidFactory;
import suwu.daopuerp.dao.factory.ProductionBillOilDaoFactory;
import suwu.daopuerp.dao.productionbill.ProductionBillLiquidDao;
import suwu.daopuerp.dao.productionbill.ProductionBillOilDao;
import suwu.daopuerp.dataservice.productionbill.ProductionBillDataService;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.entity.productionbill.ProductionBillLiquid;
import suwu.daopuerp.entity.productionbill.ProductionBillOil;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.ArrayList;
import java.util.List;

public class ProductionBillDataServiceImpl implements ProductionBillDataService {
    private ProductionBillLiquidDao productionBillLiquidDao = ProductionBillLiquidFactory.getProductionBillLiquidDao();
    private ProductionBillOilDao productionBillOilDao = ProductionBillOilDaoFactory.getProductionBillOilDao();

    @Override
    public List<ProductionBill> getAllProductionBills() {
        List<ProductionBill> productionBills = new ArrayList<>();
        productionBills.addAll(productionBillLiquidDao.findAllProductionBills());
        productionBills.addAll(productionBillOilDao.findAllProductionBills());
        return productionBills;
    }

    @Override
    public ProductionBill getProductionBillById(String id) throws IdDoesNotExistException {
        ProductionBillLiquid productionBillLiquid = productionBillLiquidDao.findProductionBillByBillId(id);
        ProductionBillOil productionBillOil = productionBillOilDao.findProductionBillByBillId(id);
        if (productionBillLiquid == null && productionBillOil == null) {
            throw new IdDoesNotExistException();
        } else {
            return productionBillLiquid == null ? productionBillOil : productionBillLiquid;
        }
    }

    @Override
    public void saveProductionBill(ProductionBill productionBill) {
        switch (productionBill.getBillType()) {
            case LIQUID:
                productionBillLiquidDao.saveProductionBill((ProductionBillLiquid) productionBill);
                break;
            case OIL:
                productionBillOilDao.saveProductionBill((ProductionBillOil) productionBill);
                break;
        }
    }

    @Override
    public void deleteProductionBill(String id) throws IdDoesNotExistException {
        try {
            productionBillOilDao.deleteProductionBill(id);
        } catch (IdDoesNotExistException e) {
            e.printStackTrace();
            productionBillLiquidDao.deleteProductionBill(id);
        }
    }
}
