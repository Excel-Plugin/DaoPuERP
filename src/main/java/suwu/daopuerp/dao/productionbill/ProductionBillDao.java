package suwu.daopuerp.dao.productionbill;

import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.exception.IdDoesNotExistException;

import java.util.List;

public interface ProductionBillDao<T extends ProductionBill> {
    List<T> findAllProductionBills();

    T findProductionBillByBillId(String billId) throws IdDoesNotExistException;

    T saveProductionBill(T productionBill);

    void deleteProductionBill(String id) throws IdDoesNotExistException;
}