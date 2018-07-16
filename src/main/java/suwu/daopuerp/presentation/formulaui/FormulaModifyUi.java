package suwu.daopuerp.presentation.formulaui;

import suwu.daopuerp.dto.formula.FormulaDto;
import suwu.daopuerp.presentation.helpui.ContentDisplayUi;
import suwu.daopuerp.presentation.helpui.ExternalLoadableUiController;
import suwu.daopuerp.presentation.helpui.FrameworkUiManager;

public abstract class FormulaModifyUi implements ContentDisplayUi<FormulaDto>, ExternalLoadableUiController {
    public void onClose() {
        FrameworkUiManager.getCurrentDialogStack().closeCurrentAndPopAndShowNext();
    }
}
