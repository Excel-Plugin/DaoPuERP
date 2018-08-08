package suwu.daopuerp.blservice.productionbill;

import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.publicdata.BillType;

import java.util.Date;
import java.util.List;

public interface ProductionBillBlService {
    /**
     * 获得所有的生产原始单
     *
     * @return
     */
    List<ProductionBillItem> getAllProductionBillItems();

    /**
     * 通过Id获得生产原始单
     *
     * @param billId
     * @return
     */
    ProductionBillDto getProductionBillDtoById(String billId) throws IdDoesNotExistException;

    /**
     * 提交生产原始单
     *
     * @param productionBillDto
     */
    void submit(ProductionBillDto productionBillDto);

    /**
     * 通过Id删除生产原始单
     *
     * @param billId
     */
    void delete(String billId) throws IdDoesNotExistException;

    /**
     * 获得下一个Id
     *
     * @param billType
     * @return
     */
    String getNextId(BillType billType);

    /**
     * 获得两个时间段之间的生产单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<ProductionBill> getBillsBetween(Date startDate, Date endDate);

    /**
     * 获得下一个测试单据
     *
     * @return
     */
    FormulaDto getNextTestBill(BillType billType);

    /**
     * @param keyword
     * @return
     */
    ProductionBillItem[] query(String keyword);
}
