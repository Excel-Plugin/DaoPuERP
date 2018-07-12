package trapx00.lightx00.client.presentation.financeui.mock;

import trapx00.lightx00.client.presentation.financeui.cashbill.CashBillUiController;
import trapx00.lightx00.client.presentation.helpui.ExternalLoadedUiPackage;
import trapx00.lightx00.client.vo.Draftable;

public class CashBillUiControllerMock extends CashBillUiController {
    /**
     * Start continuing write a draft. Returns a ExternalLoadableUiController. It can be used to set the stage without casting to specific ui controller.
     * Overrides to return a specific ui controller.
     *
     * @param draft draft
     * @return a ExternalLoadableUiController
     */
    @Override
    public ExternalLoadedUiPackage continueWriting(Draftable draft) {
        return super.continueWriting(draft);
    }
}
