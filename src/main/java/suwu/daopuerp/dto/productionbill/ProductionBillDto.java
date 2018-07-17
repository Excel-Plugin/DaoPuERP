package suwu.daopuerp.dto.productionbill;

import suwu.daopuerp.presentation.productionbillui.ProductionBillDetailUi;
import suwu.daopuerp.presentation.productionbillui.ProductionBillModifyUi;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidDetailUiController;
import suwu.daopuerp.presentation.productionbillui.liquid.ProductionBillLiquidModifyUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilDetailUiController;
import suwu.daopuerp.presentation.productionbillui.oil.ProductionBillOilModifyUiController;
import suwu.daopuerp.publicdata.BillType;

public class ProductionBillDto {
    private BillType billType;

    public ProductionBillModifyUi modifyUi() {
        switch (billType) {
            case LIQUID:
                return new ProductionBillLiquidModifyUiController();
            case OIL:
                return new ProductionBillOilModifyUiController();
            default:
                return null;
        }
    }

    public ProductionBillDetailUi detailUi() {
        switch (billType) {
            case LIQUID:
                return new ProductionBillLiquidDetailUiController();
            case OIL:
                return new ProductionBillOilDetailUiController();
            default:
                return null;
        }
    }
}
