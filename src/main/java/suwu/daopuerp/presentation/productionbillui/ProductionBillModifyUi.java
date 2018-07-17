package suwu.daopuerp.presentation.productionbillui;

import suwu.daopuerp.dto.productionbill.ProductionBillDto;
import suwu.daopuerp.presentation.helpui.ContentDisplayUi;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.FrameworkUiManager;

public abstract class ProductionBillModifyUi implements ContentDisplayUi<ProductionBillDto>, ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}