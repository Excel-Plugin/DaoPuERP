package suwu.daopuerp.blservice.productionbill;

import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.dto.productionbill.ProductionBillItem;

import java.util.List;

public interface ProductionBillService {
    List<ProductionBillItem> getAllProductionBillItems();

    ProductionBillDto getProductionBillDtoById(String billId);
}
