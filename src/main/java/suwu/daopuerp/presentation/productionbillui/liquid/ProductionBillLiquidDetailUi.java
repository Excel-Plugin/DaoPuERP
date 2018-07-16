package suwu.daopuerp.presentation.productionbillui.liquid;

import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.presentation.helpui.ContentDisplayUi;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.FrameworkUiManager;

public abstract class ProductionBillLiquidDetailUi implements ContentDisplayUi<FormulaDto>, ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
