package suwu.daopuerp.bl.productionbill.mock;

import suwu.daopuerp.blservice.productionbill.ProductionBillService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;
import suwu.daopuerp.publicdata.BillType;

import java.util.ArrayList;
import java.util.List;

public class ProductionBlServiceMock implements ProductionBillService {
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
}
