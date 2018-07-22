package suwu.daopuerp.daoimpl.productionbill;

import suwu.daopuerp.dao.productionbill.ProductionBillOilDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.entity.productionbill.ProductionBillOil;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public class ProductionBillOilDaoImpl implements ProductionBillOilDao {
    private FileService<ProductionBillOil> fileService = new FileServiceImpl<>();

    @Override
    public List<ProductionBillOil> getAllProductionBills() {
        return fileService.findAll(ProductionBillOil.class);
    }

    @Override
    public ProductionBill getProductionBillByBillId(String billId) throws IdDoesNotExistException {
        return fileService.findOne(billId, ProductionBillOil.class);
    }

    @Override
    public ProductionBillOil saveProductionBill(ProductionBillOil productionBillOil) {
        return fileService.saveTuple(productionBillOil);
    }

    @Override
    public void deleteProductionBill(String id) throws IdDoesNotExistException {
        fileService.delete(id, ProductionBillOil.class);
    }
}
