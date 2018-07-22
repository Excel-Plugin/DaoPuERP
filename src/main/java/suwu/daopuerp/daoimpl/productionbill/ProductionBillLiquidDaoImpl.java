package suwu.daopuerp.daoimpl.productionbill;

import suwu.daopuerp.dao.productionbill.ProductionBillDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.productionbill.ProductionBillLiquid;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public class ProductionBillLiquidDaoImpl implements ProductionBillDao {
    private FileService<ProductionBillLiquid> fileService = new FileServiceImpl<>();

    @Override
    public List<ProductionBillLiquid> getAllProductionBills() {
        return fileService.findAll(ProductionBillLiquid.class);
    }

    @Override
    public ProductionBillLiquid getProductionBillByBillId(String billId) throws IdDoesNotExistException {
        return fileService.findOne(billId, ProductionBillLiquid.class);
    }

    @Override
    public ProductionBillLiquid saveProductionBill(ProductionBillLiquid productionBillLiquid) {
        return fileService.saveTuple(productionBillLiquid);
    }

    @Override
    public void deleteProductionBill(String id) throws IdDoesNotExistException {
        fileService.delete(id, ProductionBillLiquid.class);
    }
}
