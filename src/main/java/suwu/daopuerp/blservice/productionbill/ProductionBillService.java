package suwu.daopuerp.blservice.productionbill;

import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.exception.IdDoesNotExistException;
import suwu.daopuerp.publicdata.BillType;

import java.util.List;

public interface ProductionBillService {
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
     * @return
     * @param billType
     */
    String getNextId(BillType billType);
}
