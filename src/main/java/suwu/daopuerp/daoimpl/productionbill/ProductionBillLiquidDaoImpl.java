package suwu.daopuerp.daoimpl.productionbill;

import suwu.daopuerp.dao.productionbill.ProductionBillLiquidDao;
import suwu.daopuerp.data.fileservice.FileService;
import suwu.daopuerp.data.fileservice.FileServiceImpl;
import suwu.daopuerp.entity.productionbill.ProductionBillLiquid;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public class ProductionBillLiquidDaoImpl implements ProductionBillLiquidDao {
    private FileService<ProductionBillLiquid> fileService = new FileServiceImpl<>();

    @Override
    public List<ProductionBillLiquid> findAllProductionBills() {
        return fileService.findAll(ProductionBillLiquid.class);
    }

    @Override
    public ProductionBillLiquid findProductionBillByBillId(String billId) {
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
