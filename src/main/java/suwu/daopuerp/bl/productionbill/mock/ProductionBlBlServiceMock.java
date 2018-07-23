package suwu.daopuerp.bl.productionbill.mock;

import suwu.daopuerp.blservice.productionbill.ProductionBillBlService;
import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.entity.productionbill.ProductionBill;
import suwu.daopuerp.publicdata.BillType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductionBlBlServiceMock implements ProductionBillBlService {
    @Override
    public List<ProductionBillItem> getAllProductionBillItems() {
        List<ProductionBillItem> productionBillItems = new ArrayList<>();
        productionBillItems.add(new ProductionBillItem(BillType.LIQUID, "1", "2", "3", "4", "5"));
        return productionBillItems;
    }

    @Override
    public ProductionBillDto getProductionBillDtoById(String billId) {
        return null;
    }

    @Override
    public void submit(ProductionBillDto productionBillDto) {

    }

    @Override
    public void delete(String billId) {

    }

    /**
     * 获得下一个Id
     *
     * @param billType
     * @return
     */
    @Override
    public String getNextId(BillType billType) {
        return null;
    }

    /**
     * 获得两个时间段之间的生产单
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<ProductionBill> getBillsBetween(Date startDate, Date endDate) {
        return null;
    }

    /**
     * 获得下一个测试单据
     *
     * @return
     */
    @Override
    public FormulaDto getNextTestBill(BillType billType) {
        return null;
    }
}
