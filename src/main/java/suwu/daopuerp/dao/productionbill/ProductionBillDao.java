package suwu.daopuerp.dao.productionbill;

import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public interface ProductionBillDao<T extends ProductionBill> {
    List<T> getAllProductionBills();

    ProductionBill getProductionBillByBillId(String billId) throws IdDoesNotExistException;

    T saveProductionBill(T productionBill);

    void deleteProductionBill(String id) throws IdDoesNotExistException;
}