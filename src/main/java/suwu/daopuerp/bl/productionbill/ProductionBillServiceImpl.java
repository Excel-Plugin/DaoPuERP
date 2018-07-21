package suwu.daopuerp.bl.productionbill;

import suwu.daopuerp.blservice.productionbill.ProductionBillService;
import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;

import java.util.List;

public class ProductionBillServiceImpl implements ProductionBillService {
    @Override
    public List<ProductionBillItem> getAllProductionBillItems() {
        return null;
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
}
