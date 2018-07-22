package suwu.daopuerp.dataservice.productionbill;

import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public interface ProductionBillDataService {
    List<ProductionBill> getAllProductionBills();

    ProductionBill getProductionBillById(String id) throws IdDoesNotExistException;

    void saveProductionBill(ProductionBill productionBill);

    void deleteProductionBill(String id) throws IdDoesNotExistException;
}
